package com.raveleen.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by Святослав on 12.03.2017.
 */
@Entity
public class UserRate {
    @Id
    @GeneratedValue
    private long id;

    private int mark;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user")
    private CustomUser user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "event_id")
    private Event event;

    public UserRate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
