package com.raveleen.repositories;

import com.raveleen.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Святослав on 12.03.2017.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
