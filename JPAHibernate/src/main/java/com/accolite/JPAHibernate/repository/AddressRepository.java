package com.accolite.JPAHibernate.repository;

import com.accolite.JPAHibernate.entity.Address;
import jakarta.persistence.EmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
