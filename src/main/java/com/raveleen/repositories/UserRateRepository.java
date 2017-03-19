package com.raveleen.repositories;

import com.raveleen.entities.UserRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface UserRateRepository extends JpaRepository<UserRate, Long> {
    UserRate getByIdAndUserId(long id1, long id2);

    int getAverageMark(long id);

    int getNumberOfRateForEvent(long id);
}
