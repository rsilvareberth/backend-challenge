package com.invillia.acme.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByStoreIdAndCityIgnoreCaseAndZip(Long storeId,String city, String zip );
	   
}

