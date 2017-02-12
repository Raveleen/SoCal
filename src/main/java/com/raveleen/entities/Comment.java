package com.raveleen.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Святослав on 31.12.2016.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 500)
    private String text;

    @OneToOne
    @JoinColumn(name = "from_user_id")
    private CustomUser from;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    private Date createDate;

    public Comment() {
    }

    public Comment(CustomUser from, String text, Post post) {
        this.from = from;
        this.text = text;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getFrom() {
        return from;
    }

    public void setFrom(CustomUser from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
