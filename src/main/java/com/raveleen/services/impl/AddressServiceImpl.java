package com.raveleen.services.impl;

import com.raveleen.repositories.AddressRepository;
import com.raveleen.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
}
