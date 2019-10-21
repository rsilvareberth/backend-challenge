package com.invillia.acme.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Store> findByStatusIgnoreCase(String status);
	
}

