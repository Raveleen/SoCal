package com.raveleen.services.impl;

import com.raveleen.entities.Event;
import com.raveleen.repositories.EventRepository;
import com.raveleen.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findByHostIdOrderByEventDateDesc(long id, int from) {
        return null;
    }

    @Override
    public List<Event> getFollowingsEvents(long id1, int from) {
        return null;
    }

    @Override
    public Event getById(long id) {
        return null;
    }

    @Override
    public void updateEvent(Event event) {

    }

    @Override
    public void deleteEventById(long id) {

    }

    @Override
    public Event addEvent(Event event) {
        return null;
    }
}
