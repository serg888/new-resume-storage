package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 10.10.2016.
 */
public class Organization {
    private final Link homePage;

    private final List<BlockDescription> blockDescriptions = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        blockDescriptions.add(new BlockDescription(startDate, endDate, title, description));
    }

    public void addNewBlockDescription(LocalDate startDate, LocalDate endDate, String title, String description) {
        blockDescriptions.add(new BlockDescription(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return blockDescriptions.equals(that.blockDescriptions);

    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + blockDescriptions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", blockDescriptions=" + blockDescriptions +
                '}';
    }
}
