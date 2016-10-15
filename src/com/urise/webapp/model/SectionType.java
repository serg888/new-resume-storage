package com.urise.webapp.model;

import java.io.Serializable;

/**
 * Created by Сергей on 10.10.2016.
 */
public enum SectionType implements Serializable{
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }
}
