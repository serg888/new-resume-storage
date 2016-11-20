package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.ResumeInit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Сергей on 09.11.2016.
 */
public class ResumeServlet extends HttpServlet {
    protected static Resume R1;
    protected static Resume R2;
    protected static Resume R3;
    protected static Resume R4;
    private static Resume resume;
    private static List<Resume> listResumes = new ArrayList<>();

    private static String UUID_1;

    static {
        List<Resume> list = ResumeInit.getResumes();
        R1 = list.get(0);
        R2 = list.get(1);
        R3 = list.get(2);
        R4 = list.get(3);
        UUID_1 = R1.getUuid();
        listResumes.add(R1);
        listResumes.add(R2);
        listResumes.add(R3);
        listResumes.add(R4);
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
        String number = request.getParameter("number");
        response.getWriter().write(name);
        response.getWriter().write(number);
        int i = Integer.parseInt(number);

//        int number=Integer.parseInt(request.getParameter("number"));

        resume = R2;
        try {
            resume = listResumes.get(i);
        } catch (Exception e) {

        }
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
                        "<td>" + resume.getUuid() + "</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" + "full_name" + "</td>" +
                        "<td>" + resume.getFullName() + "</td>" +
                        "</tr>"
        );
        for (Map.Entry<ContactType, String> map : resume.getContacts().entrySet()) {
            response.getWriter().write(
                    "<tr>" +
                            "<td>" + map.getKey() + "</td>" +
                            "<td>" + map.getValue() + "</td>" +
                            "</tr>");
        }
        for (Map.Entry<SectionType, Section> map : resume.getSections().entrySet()) {
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
