package com.raveleen.services;

import com.raveleen.entities.Event;

import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface EventService {
    List<Event> findByHostIdOrderByEventDateDesc(long id, int from);

    List<Event> getFollowingsEvents(long id1, int from);

    Event getById(long id);

    void updateEvent(Event event);

    void deleteEventById(long id);

    Event addEvent(Event event);
}
