package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Сергей on 09.11.2016.
 */
public class ResumeServlet extends HttpServlet {
    static Resume R1;

    static {
        R1 = new Resume(UUID.randomUUID().toString(), "Name1");


        R1.addContact(ContactType.MAIL, "serg888@mail.ru");
        R1.addContact(ContactType.PHONE, "8-933-300-4498");

        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("achievment1", "achievment1", "achievment2"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Turbo Pascal", "1C", "Java"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(
                "<html>" +
                        "<head>" +
                        "<title>Resume</title>" +
                        "</head>" +
                        "<body>" +
                        "<table border=\"1\">");
        response.getWriter().write(
                "<tr>" +
                        "<td>" + "uuid" + "</td>" +
                        "<td>" + R1.getUuid() + "</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" + "full_name" + "</td>" +
                        "<td>" + R1.getFullName() + "</td>" +
                        "</tr>"
        );
        for (Map.Entry<ContactType, String> map : R1.getContacts().entrySet()) {
            response.getWriter().write(
                    "<tr>" +
                            "<td>" + map.getKey() + "</td>" +
                            "<td>" + map.getValue() + "</td>" +
                            "</tr>");
        }
        for (Map.Entry<SectionType, Section> map : R1.getSections().entrySet()) {
            response.getWriter().write(
                    "<tr>" +
                            "<td>" + map.getKey() + "</td>" +
                            "<td>" + map.getValue() + "</td>" +
                            "</tr>");
        }

        response.getWriter().write(
                "</table>" +
                        "</body>" +
                        "</html>"
        );


    }
}
