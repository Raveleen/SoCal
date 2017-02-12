package com.raveleen.repositories;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    @Query("SELECT u FROM ProfileImage u WHERE u.id = :id")
    ProfileImage findById(@Param("id") long id);
}
