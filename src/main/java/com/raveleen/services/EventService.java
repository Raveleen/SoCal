package com.raveleen.services;

import com.raveleen.entities.Event;
import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface EventService {
    List<Event> getFollowingsEvents(long id1, Date time, int from);

    Event getById(long id);

    void updateEvent(Event event);

    void deleteEventById(long id);

    Event addEvent(Event event);

    List<Event> getFutureEvents(long id, Date time, int from);

    List<Event> getPastEvents(long id, Date time, int from);
}
