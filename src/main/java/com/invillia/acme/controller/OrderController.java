package com.invillia.acme.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.acme.model.Address;
import com.invillia.acme.model.AddressRepository;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderRepository;
import com.invillia.acme.model.Store;
import com.invillia.acme.model.StoreRepository;

@Component
public class OrderController {

	@Autowired
	OrderRepository orderRepository;

//	@Autowired
//	AddressRepository addressRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	public List<Store> findByStatus(String status) {
		List<Store> orders = orderRepository.findByStatusIgnoreCase(status);

		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).getAddress().size();
		}
		return orders;

	}

	public Order updateStore(Order order) throws IOException {

		Order persistentOrder = Optional.ofNullable(orderRepository.findById(order.getOrderId()));
//			
//			store.setStoreId(persistentStore.get(0).getStoreId());
//			store = defineAddress(store);
//
//			org.springframework.beans.BeanUtils.copyProperties(store, persistentStore.get(0));
//			storeRepository.save(persistentStore.get(0));
//
		return order;

	}

//	private Store defineAddress(Store store) {
//		for (int i = 0; i < store.getAddress().size(); i++) {
//			List<Address> addresses = addressRepository.findByStoreIdAndCityIgnoreCaseAndZip(store.getStoreId(),
//					store.getAddress().get(i).getCity(), store.getAddress().get(i).getZip());
//
//			if (!addresses.isEmpty()) {
//				store.getAddress().get(i).setAddressId(addresses.get(i).getAddressId());
//				store.getAddress().get(i).setStoreId(store.getStoreId());
//			}
//		}
//		return store;
//	}
}
