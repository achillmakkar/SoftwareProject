package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.constants.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Occupancy entity
 */


@Entity

// The following annotations are for Lombok that we do not have to create the Getters,
// a constructor with all arguments, a constructor with no arguments an a toString() Methos
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class Occupancy {

    // the variable id is marked as the primary key in the database table
    @Id
    // The primary key is generated by the database via an autoincrement field
    // See https://thoughts-on-java.org/jpa-generate-primary-keys/ for alternatives
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    private int rooms;

    private int usedrooms;

    private int beds;

    private int usedbeds;

    private int year;

    private int month;


    public static OccupancyBuilder builder() {
        return new OccupancyBuilder();
    }

    public Occupancy updateWith(final Occupancy other) {
        this.rooms = other.rooms;
        this.usedrooms = other.usedrooms;
        this.beds = other.beds;
        this.usedbeds = other.usedbeds;
        this.year = other.year;
        this.month = other.month;

        return this;
    }


    public static class OccupancyBuilder {
        private int id;
        private int rooms;
        private int usedrooms;
        private int beds;
        private int usedbeds;
        private int year;
        private int month;
        public OccupancyBuilder() {
        }

        @SuppressWarnings("checkstyle:hiddenfield")
        public OccupancyBuilder id(final int id) {
            this.id = id;
            return this;
        }

        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder rooms(final int rooms) {
            this.rooms = rooms;
            return this;
        }

        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder usedRooms(final int usedrooms) {
            this.usedrooms = usedrooms;
            return this;
        }

        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder beds(final int beds) {
            this.beds = beds;
            return this;
        }

        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder usedBeds(final int usedbeds) {
            this.usedbeds = usedbeds;
            return this;
        }


        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder year(final int year) {
            this.year = year;
            return this;
        }

        @SuppressWarnings("checkstyle:hiddenField")
        public OccupancyBuilder month(final int month) {
            this.month = month;
            return this;
        }



        public Occupancy build() {
            return new Occupancy(id, rooms, usedrooms, beds, usedbeds, year, month);
        }

        public String toString() {
            return "Occupancy.OccupancyBuilder(id=" + this.id + ", rooms=" + this.rooms + ", usedRooms="
                    + this.usedrooms + ", beds=" + this.beds + ", usedBeds=" + this.usedbeds
                    + ", year=" + this.year + ", month=" + this.month + ")";
        }
    }


}
