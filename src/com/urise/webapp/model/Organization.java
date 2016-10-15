package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.urise.webapp.util.DateUtil;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;



/**
 * Created by Сергей on 10.10.2016.
 */
public class Organization implements Serializable{
    private final Link homePage;

    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String url, Position...positions) {
        this(new Link(name,url), Arrays.asList(positions));
    }

    public Organization(Link link, List<Position> positions) {
        this.homePage=link;
        this.positions = positions;
    }

    public void addNewBlockDescription(LocalDate startDate, LocalDate endDate, String title, String description) {
        positions.add(new Position(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);

    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    /**
     * Created by Сергей on 12.10.2016.
     */
    public static class Position implements Serializable{
        private static final long serialVersionUID=1L;

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(LocalDate startDate, LocalDate endDate, String title, String description ) {
            Objects.requireNonNull(startDate,"startDate must not be null");
            Objects.requireNonNull(endDate,"endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title=title;
            this.description=description;

        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(of(startYear,startMonth),NOW,title,description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description){
            this(of(startYear,startMonth),of(endYear,endMonth),title,description);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;

        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
