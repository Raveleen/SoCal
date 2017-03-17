package com.raveleen.services.impl;

import com.raveleen.entities.Address;
import com.raveleen.repositories.AddressRepository;
import com.raveleen.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Святослав on 12.03.2017.
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public Address getById(long id) {
        return addressRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateAddress(Address address) {
        addressRepository.saveAndFlush(address);
    }

    @Override
    @Transactional
    public void deleteAddressById(long id) {
        addressRepository.delete(id);
    }

    @Override
    @Transactional
    public Address addAddress(Address address) {
        return addressRepository.saveAndFlush(address);
    }
}
