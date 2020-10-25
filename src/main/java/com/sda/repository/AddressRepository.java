package com.sda.repository;

import com.sda.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
