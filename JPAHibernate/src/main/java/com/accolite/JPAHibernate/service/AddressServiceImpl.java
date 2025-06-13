package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.entity.Address;
import com.accolite.JPAHibernate.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

}
