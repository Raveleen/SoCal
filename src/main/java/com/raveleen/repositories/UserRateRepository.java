package com.raveleen.repositories;

import com.raveleen.entities.UserRate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface UserRateRepository extends JpaRepository<UserRate, Long> {
}
