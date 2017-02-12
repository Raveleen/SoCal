package com.raveleen.services.impl;

import com.raveleen.entities.Message;
import com.raveleen.repositories.MessageRepository;
import com.raveleen.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 05.02.2017.
 */
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Message> findByDialogIdOrderByCreateDateDesc(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return messageRepository.findByDialogIdOrderByCreateDateDesc(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public Message getById(long id) {
        return messageRepository.findOne(id);
    }

    @Override
    @Transactional
    public Message addMessage(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfUnreadMessages(long id, long usId) {
        return messageRepository.numberOfUnreadMessages(id, usId);
    }

    @Override
    public List<Message> getNewMessages(long id, long time) {
        return messageRepository.getNewMessages(id, new Date(time));
    }
}
