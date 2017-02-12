package com.raveleen.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 31.12.2016.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 5000)
    private String text;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "author_id")
    private CustomUser author;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="image_id")
    private Image image;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "liked_id", referencedColumnName="id"))
    private List<CustomUser> likedBy = new ArrayList<>();

    @CreationTimestamp
    private Date createDate;

    public Post() {
    }

    public Post(String text, CustomUser author, List<Comment> comments) {
        this.text = text;
        this.author = author;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CustomUser getAuthor() {
        return author;
    }

    public void setAuthor(CustomUser author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<CustomUser> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<CustomUser> likedBy) {
        this.likedBy = likedBy;
    }

    public void addLike(CustomUser customUser) {
        likedBy.add(customUser);
    }

    public void removeLike(CustomUser customUser) {
        likedBy.remove(customUser);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}
