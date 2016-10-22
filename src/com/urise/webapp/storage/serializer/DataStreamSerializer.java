package com.urise.webapp.storage.serializer;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Сергей on 15.10.2016.
 */
public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            //TODO implements section
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section sect = entry.getValue();
                if (sect instanceof TextSection) textSectionSerialize((TextSection) sect, dos);
                if (sect instanceof ListSection) listSectionSerialize((ListSection) sect, dos);
                if (sect instanceof OrganizationSection) organizationSectionSerialize((OrganizationSection) sect, dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(contactType, value);
            }

            //TODO implements section
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                String section = dis.readUTF();
                if (section.equals("TextSection")) {
                    TextSection textSection = textSectionDeserialise(dis);
                    resume.addSection(sectionType, textSection);
                }

                if (section.equals("ListSection")) {
                    ListSection listSection = listSectionDeserialise(dis);
                    resume.addSection(sectionType, listSection);
                }

                if (section.equals("OrganizationSection")) {
                    OrganizationSection organizationSection = organizationSectionDeserialize(dis);
                    resume.addSection(sectionType, organizationSection);
                }
            }
            return resume;
        }
    }

    private void textSectionSerialize(TextSection textSection, DataOutputStream dos) throws IOException {
        dos.writeUTF("TextSection");
        dos.writeInt(1);
        dos.writeUTF(textSection.getContent());
    }

    private void listSectionSerialize(ListSection listSection, DataOutputStream dos) throws IOException {
        dos.writeUTF("ListSection");
        int size = listSection.getItems().size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            dos.writeUTF(listSection.getItems().get(i));
        }
    }

    private void organizationSectionSerialize(OrganizationSection organizationSection, DataOutputStream dos) throws IOException {
        dos.writeUTF("OrganizationSection");
        int size = organizationSection.getOrganizations().size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            organizationSerialize(organizationSection.getOrganizations().get(i), dos);
        }
    }

    private void organizationSerialize(Organization organization, DataOutputStream dos) throws IOException {
        dos.writeUTF(organization.getHomePage().getName());
        boolean isHasUrl = (organization.getHomePage().getUrl() != null);
        dos.writeInt((isHasUrl) ? 1 : 0);
        dos.writeUTF((isHasUrl) ? organization.getHomePage().getUrl() : "null URL");
        int size = organization.getPositions().size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            Organization.Position position = organization.getPositions().get(i);
            boolean isHasDescription = (position.getDescription() != null);
            dos.writeInt((isHasDescription) ? 1 : 0);
            dos.writeInt(position.getStartDate().getYear());
            dos.writeInt(position.getStartDate().getMonth().getValue());
            dos.writeInt(position.getEndDate().getYear());
            dos.writeInt(position.getEndDate().getMonth().getValue());
            dos.writeUTF(position.getTitle());
            dos.writeUTF((isHasDescription) ? position.getDescription() : "null description");
        }
    }

    private TextSection textSectionDeserialise(DataInputStream dis) throws IOException {
        dis.readInt();
        String description = dis.readUTF();
        return new TextSection(description);
    }

    private ListSection listSectionDeserialise(DataInputStream dis) throws IOException {
        List<String> item = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            item.add(dis.readUTF());
        }
        return new ListSection(item);
    }

    private OrganizationSection organizationSectionDeserialize(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int numberOfOrganization = dis.readInt();
        for (int i1 = 0; i1 < numberOfOrganization; i1++) {
            List<Organization.Position> positions = new ArrayList<>();
            String name = dis.readUTF();
            int isHasURL = dis.readInt();
            String tempURL = dis.readUTF();
            String url = (isHasURL == 1) ? tempURL : null;
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                int isHasDescription = dis.readInt();
                int startYear = dis.readInt();
                int startMonth = dis.readInt();
                int endYear = dis.readInt();
                int endMonth = dis.readInt();
                String title = dis.readUTF();
                String s = dis.readUTF();
                String description = (isHasDescription == 1) ? s : null;
                Organization.Position position = new Organization.Position(startYear, Month.of(startMonth)
                        , endYear, Month.of(endMonth), title, description);
                positions.add(position);
            }
            organizations.add(new Organization(new Link(name, url), positions));
        }
        return new OrganizationSection(organizations);
    }

}
