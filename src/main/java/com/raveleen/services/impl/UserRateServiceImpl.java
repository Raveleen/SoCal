package com.raveleen.services.impl;

import com.raveleen.entities.UserRate;
import com.raveleen.repositories.UserRateRepository;
import com.raveleen.services.UserRateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class UserRateServiceImpl implements UserRateService {
    @Autowired
    private UserRateRepository userRateRepository;

    @Override
    @Transactional(readOnly = true)
    public UserRate getByIdAndUserId(long eventId, long userId) {
        return userRateRepository.getByIdAndUserId(eventId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRate getById(long id) {
        return userRateRepository.findOne(id);
    }

    @Override
    @Transactional
    public UserRate updateRate(UserRate userRate) {
        return userRateRepository.saveAndFlush(userRate);
    }

    @Override
    @Transactional
    public void deleteUserRateById(long id) {
        userRateRepository.delete(id);
    }

    @Override
    @Transactional
    public UserRate addUserRate(UserRate userRate) {
        return userRateRepository.saveAndFlush(userRate);
    }

    @Override
    @Transactional(readOnly = true)
    public double getAverageMark(long eventId) {
        return userRateRepository.getAverageMark(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfRateForEvent(long eventId) {
        return userRateRepository.getNumberOfRateForEvent(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isThereMarks(long id) {
        return userRateRepository.isThereMarks(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserRated(long eventId, long userId) {
        return userRateRepository.isUserRated(eventId, userId);
    }
}
