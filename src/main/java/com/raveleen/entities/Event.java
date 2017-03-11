package com.raveleen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Святослав on 11.03.2017.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private CustomUser host;

    @Column(name = "event_date")
    private Date eventDate;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getHost() {
        return host;
    }

    public void setHost(CustomUser host) {
        this.host = host;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
