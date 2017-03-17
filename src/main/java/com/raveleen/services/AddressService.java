package com.raveleen.services;

import com.raveleen.entities.Address;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface AddressService {
    Address getById(long id);

    void updateAddress(Address address);

    void deleteAddressById(long id);

    Address addAddress(Address address);
}
