package com.raveleen.repositories;

import com.raveleen.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByHostIdOrderByEventDateDesc(long id, Pageable pageable);

    @Query("SELECT u FROM Event u "
            + "INNER JOIN u.host x "
            + "WHERE x.id IN (SELECT d.id FROM CustomUser d "
            + "INNER JOIN d.followers c WHERE c.id = :id1) ORDER BY u.eventDate DESC")
    List<Event> getFollowingsEvents(@Param("id1") long id1, Pageable pageable);
}
