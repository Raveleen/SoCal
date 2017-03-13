package com.raveleen.services.impl;

import com.raveleen.repositories.UserRateRepository;
import com.raveleen.services.UserRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class UserRateServiceImpl implements UserRateService {
    @Autowired
    private UserRateRepository userRateRepository;
}
