package com.raveleen.repositories;

import com.raveleen.entities.UserRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface UserRateRepository extends JpaRepository<UserRate, Long> {
    @Query("SELECT u FROM UserRate u INNER JOIN u.user c INNER JOIN u.event b WHERE c.id = :id2 AND b.id = :id1")
    UserRate getByIdAndUserId(@Param("id1") long id1, @Param("id2") long id2);

    @Query("SELECT AVG(u.mark) FROM Event c INNER JOIN c.userRates u WHERE c.id = :id")
    double getAverageMark(@Param("id") long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Event c "
            + "INNER JOIN c.userRates u "
            + "WHERE c.id = :id AND u.mark > 0")
    boolean isThereMarks(@Param("id") long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserRate u INNER JOIN u.user c INNER JOIN u.event b WHERE c.id = :userId AND b.id = :eventId AND u.mark > 0")
    boolean isUserRated(@Param("eventId") long eventId, @Param("userId") long userId);

    @Query("SELECT COUNT(u) FROM Event c INNER JOIN c.userRates u WHERE c.id = :id AND u.mark IS NOT NULL")
    int getNumberOfRateForEvent(@Param("id") long id);
}
