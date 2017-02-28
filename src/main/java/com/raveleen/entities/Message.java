package com.raveleen.entities;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Created by Святослав on 31.12.2016.
 */
@Entity
public class Message {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 1000)
    private String text;

    @OneToOne
    @JoinColumn(name = "from_user_id")
    private CustomUser from;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isread;

    @CreationTimestamp
    private Date createDate;

    public Message() {
    }

    public Message(CustomUser customUser, String text) {
        this.text = text;
        this.from = customUser;
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

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public CustomUser getFrom() {
        return from;
    }

    public void setFrom(CustomUser from) {
        this.from = from;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }
}
