package com.raveleen.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 11.03.2017.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30)
    private String title;

    @Column(length = 1000)
    private String info;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "host_id")
    private CustomUser host;

    @OneToMany(mappedBy = "event")
    private List<UserRate> userRates = new ArrayList<>();

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="address")
    private Address address;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<UserRate> getUserRates() {
        return userRates;
    }

    public void setUserRates(List<UserRate> userRates) {
        this.userRates = userRates;
    }
}
