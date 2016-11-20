package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Сергей on 19.11.2016.
 */
public class ResumeInit {

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    protected static final Resume R1;
    protected static final Resume R2;
    protected static final Resume R3;
    protected static final Resume R4;

    private static List<Resume> list = new ArrayList<>();

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");


        R1.addContact(ContactType.MAIL, "serg888@mail.ru");
        R1.addContact(ContactType.PHONE, "8-933-300-4498");
        R4.addContact(ContactType.SKYPE, "skype4");
        R4.addContact(ContactType.PHONE, "phone4");

        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("achievment1", "achievment1", "achievment2"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Turbo Pascal", "1C", "Java"));

        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "contenet1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))
                ));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organisation12", "http://organisation12.ru")
                ));


        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "phone2");
        R2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1")
                        )));
        list.add(R1);
        list.add(R2);
        list.add(R3);
        list.add(R4);

    }

    public static List getResumes() {
       return list;
    }

}
