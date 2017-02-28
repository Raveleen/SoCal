package com.raveleen.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Created by Святослав on 03.01.2017.
 */
@Entity
public class Dialog {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private CustomUser user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private CustomUser user2;

    @OneToMany(mappedBy = "dialog")
    private List<Message> messages = new ArrayList<>();

    private Date lastMessageDate;

    @CreationTimestamp
    private Date createDate;

    public Dialog() {
    }

    public Dialog(CustomUser user1, CustomUser user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getUser1() {
        return user1;
    }

    public void setUser1(CustomUser user1) {
        this.user1 = user1;
    }

    public CustomUser getUser2() {
        return user2;
    }

    public void setUser2(CustomUser user2) {
        this.user2 = user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
