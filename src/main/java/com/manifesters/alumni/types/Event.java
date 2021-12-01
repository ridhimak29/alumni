package com.manifesters.alumni.types;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private long id;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "EVENT_LOCATION")
    private String venue;

    @Column(name = "EVENT_DESCRIPTION")
    private String description;

    @Column(name = "START_DATE_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;

    @Column(name= "image")
    private String imagePath;

    @Column(name = "TICKET_PRICE")
    private double price;

    public Event (String eventName, String venue, String description, Date date, String imagePath, double price) {
        this.eventName = eventName;
        this.venue = venue;
        this.description = description;
        this.date = date;
        this.imagePath = imagePath;
        this.price = price;
    }

    public Event(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(eventName, event.eventName) && Objects.equals(venue, event.venue) && Objects.equals(description, event.description) && Objects.equals(date, event.date) && Objects.equals(imagePath, event.imagePath)&& Objects.equals(price, event.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, venue, description, date, imagePath, price);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + eventName + '\'' +
                ", venue='" + venue + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", imagePath='" + imagePath + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
