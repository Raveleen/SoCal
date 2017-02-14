package com.raveleen.repositories;

import com.raveleen.entities.CustomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 03.01.2017.
 */
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    @Query("SELECT u FROM CustomUser u WHERE u.login = :login")
    CustomUser findByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u INNER JOIN u.followers c WHERE (u.id = :id1) AND (c.id = :id2)")
    boolean isFollowing(@Param("id1") long id1, @Param("id2") long id2);

    @Query("SELECT COUNT(u) FROM CustomUser u INNER JOIN u.followers c WHERE u.id = :id1")
    int getNumberOfFollowers(@Param("id1") long id1);

    @Query("SELECT COUNT(u) FROM CustomUser u INNER JOIN u.followers c WHERE c.id = :id1")
    int getNumberOfFollowings(@Param("id1") long id1);

    @Query("SELECT c FROM Post u INNER JOIN u.likedBy c WHERE u.id = :id")
    List<CustomUser> byLikedPosts(@Param("id")long id, Pageable pageable);

    List<CustomUser> findByLoginStartingWith(String login, Pageable pageable);

    @Query("SELECT c FROM CustomUser u INNER JOIN u.followers c WHERE (u.id = :id1)")
    List<CustomUser> followersByFollowedId(@Param("id1")long id, Pageable pageable);

    @Query("SELECT u FROM CustomUser u INNER JOIN u.followers c WHERE (c.id = :id1)")
    List<CustomUser> followingByFollowedId(@Param("id1")long id, Pageable pageable);

    @Query("SELECT u FROM CustomUser u INNER JOIN u.followers c WHERE c.id IN (SELECT u.id FROM CustomUser u INNER JOIN u.followers c WHERE c.id = :id1)")
    List<CustomUser> recFollowingByFollowedId(@Param("id1")long id, Pageable pageable);
}
