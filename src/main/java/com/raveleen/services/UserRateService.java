package com.raveleen.services;

import com.raveleen.entities.UserRate;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface UserRateService {
    UserRate getByIdAndUserId(long id1, long id2);

    UserRate getById(long id);

    UserRate updateRate(UserRate userRate);

    void deleteUserRateById(long id);

    UserRate addUserRate(UserRate userRate);

    double getAverageMark(long id);

    int getNumberOfRateForEvent(long id);
}
