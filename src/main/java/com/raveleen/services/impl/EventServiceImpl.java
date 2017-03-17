package com.raveleen.services.impl;

import com.raveleen.entities.Event;
import com.raveleen.repositories.EventRepository;
import com.raveleen.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Event> getFollowingsEvents(long id1, Date time, int from) {
        int counter;
        if (from == 0) {
            counter = 0;
        } else {
            counter = from / 10;
        }
        Pageable temp = new PageRequest(counter, 10);
        return eventRepository.getFollowingsEvents(id1, time, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getById(long id) {
        return eventRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateEvent(Event event) {
        eventRepository.saveAndFlush(event);
    }

    @Override
    @Transactional
    public void deleteEventById(long id) {
        eventRepository.delete(id);
    }

    @Override
    @Transactional
    public Event addEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getFutureEvents(long id, Date time, int from) {
        int counter;
        if (from == 0) {
            counter = 0;
        } else {
            counter = from / 10;
        }
        Pageable temp = new PageRequest(counter, 10);
        return eventRepository.getFutureEvents(id, time, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getPastEvents(long id, Date time, int from) {
        int counter;
        if (from == 0) {
            counter = 0;
        } else {
            counter = from / 10;
        }
        Pageable temp = new PageRequest(counter, 10);
        return eventRepository.getPastEvents(id, time, temp);
    }
}
