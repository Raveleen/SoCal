package com.raveleen.entities;

import javax.persistence.*;

/**
 * Created by Святослав on 15.01.2017.
 */
@Entity
public class ProfileImage {
    @Id
    @GeneratedValue
    @Column(name = "profile_image_id")
    private long id;

    @Column(length = 16777215)
    private byte[] body;

    public ProfileImage() {
    }

    public ProfileImage(byte[] body) {
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
