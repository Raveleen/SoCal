package com.raveleen.entities;

import javax.persistence.*;

/**
 * Created by Святослав on 14.01.2017.
 */
@Entity
public class Image {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 16777215)
    private byte[] body;

    public Image() {
    }

    public Image(byte[] body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
