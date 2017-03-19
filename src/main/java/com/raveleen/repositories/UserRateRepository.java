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
    @Query("SELECT u FROM userRate u WHERE u.host_id = :id2 AND u.event_id = :id1")
    UserRate getByIdAndUserId(@Param("id1") long id1, @Param("id2") long id2);

    @Query("SELECT AVG(u.mark) FROM userRate u WHERE u.event_id = :id")
    double getAverageMark(@Param("id") long id);

    @Query("SELECT COUNT(u) FROM userRate u WHERE u.host_id = :id")
    int getNumberOfRateForEvent(@Param("id") long id);
}
