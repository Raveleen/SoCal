package com.raveleen.services;

import com.raveleen.entities.CustomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 03.01.2017.
 */
public interface UserService {
    CustomUser getUserByLogin(String login);

    CustomUser getUserById(long id);

    boolean existsByLogin(String login);

    void addUser(CustomUser customUser);

    void updateUser(CustomUser customUser);

    int getNumberOfFollowers(long id);

    int getNumberOfFollowings(long id);

    void addFollower(CustomUser customUser, CustomUser customUser2);

    void deleteFollower(CustomUser customUser, CustomUser customUser2);

    boolean isFollowing(CustomUser customUser , CustomUser customUser2);

    List<CustomUser> findByLoginStartingWith(String login, int from);

    List<CustomUser> byLikedPosts(long id, int from);

    List<CustomUser> followersByFollowedId(long id, int from);

    List<CustomUser> followingByFollowedId(long id, int from);

    List<CustomUser> recFollowingByFollowedId(long id, int from);
}
