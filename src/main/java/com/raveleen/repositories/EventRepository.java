package com.raveleen.repositories;

import com.raveleen.entities.Event;
import com.raveleen.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT u FROM Event u "
            + "INNER JOIN u.host x "
            + "WHERE x.id IN (SELECT d.id FROM CustomUser d "
            + "INNER JOIN d.followers c WHERE c.id = :id1) "
            + "AND u.eventDate < :lsdate "
            + "ORDER BY u.eventDate DESC")
    List<Event> getFollowingsEvents(@Param("id1") long id1, @Param("lsdate") Date time, Pageable pageable);

    @Query("SELECT u FROM Event u "
            + "INNER JOIN u.host b "
            + "WHERE b.id = :host AND u.eventDate > :lsdate")
    List<Event> getFutureEvents(@Param("host") long id, @Param("lsdate") Date time, Pageable pageable);

    @Query("SELECT u FROM Event u "
            + "INNER JOIN u.host b "
            + "WHERE b.id = :host AND u.eventDate < :lsdate")
    List<Event> getPastEvents(@Param("host") long id, @Param("lsdate") Date time, Pageable pageable);
}
