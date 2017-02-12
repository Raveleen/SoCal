package com.raveleen.services;

import com.raveleen.entities.Message;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface MessageService {
    List<Message> findByDialogIdOrderByCreateDateDesc(long id, int from);
    Message getById(long id);
    Message addMessage(Message message);
    int getNumberOfUnreadMessages(long id, long usId);
    List<Message> getNewMessages(long id, long time);
}
