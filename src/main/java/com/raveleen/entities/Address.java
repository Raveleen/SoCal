package com.raveleen.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Святослав on 12.03.2017.
 */
@Entity
public class Address {
    @Id
    @GeneratedValue
    private long id;

    public Address() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
