package com.raveleen.services.impl;

import com.raveleen.entities.CustomUser;
import com.raveleen.repositories.UserRepository;
import com.raveleen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Святослав on 03.01.2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Override
    @Transactional
    public void updateUser(CustomUser customUser) {
        userRepository.save(customUser);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfFollowers(long id) {
        return userRepository.getNumberOfFollowers(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfFollowings(long id) {
        return userRepository.getNumberOfFollowings(id);
    }

    @Override
    @Transactional
    public void addFollower(CustomUser follows, CustomUser follow) {
        follow.addFollower(follows);
        userRepository.saveAndFlush(follow);
    }

    @Override
    @Transactional
    public void deleteFollower(CustomUser follows, CustomUser unFollow) {
        unFollow.deleteFollower(follows);
        userRepository.saveAndFlush(unFollow);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFollowing(CustomUser customUser, CustomUser customUser2) {
        return userRepository.isFollowing(customUser2.getId(), customUser.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> findByLoginStartingWith(String login, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return userRepository.findByLoginStartingWith(login, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> byLikedPosts(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return userRepository.byLikedPosts(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> followersByFollowedId(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return userRepository.followersByFollowedId(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> followingByFollowedId(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return userRepository.followingByFollowedId(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> recFollowingByFollowedId(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 3;
        }
        Pageable temp = new PageRequest(a, 3);
        return userRepository.recFollowingByFollowedId(id, temp);
    }
}
