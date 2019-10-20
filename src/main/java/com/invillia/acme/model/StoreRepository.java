package com.invillia.acme.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	List<Store> findByNameIgnoreCase(String name);
	
	
	@Query(value = "select s.name,d.store_id,d.fulladdress,d.state,d.zip,d.city,d.country from "
			+ " acme.store s inner join acme.address d  on s.store_id = d.store_id"
			+ " where d.zip = ?1 and d.city = ?2 ", nativeQuery = true)
	List<Store> findByZipAndCity(String zip,String city);
	
}

