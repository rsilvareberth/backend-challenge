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

	@Autowired
	AddressRepository addressRepository;

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

	public Order updateOrder(Order order) throws IOException {
		Optional<Order> persistentOrder = findById(order.getOrderId());
		
			order.setOrderId(persistentOrder.get().getOrderId());
			order = defineAddress(order);

			org.springframework.beans.BeanUtils.copyProperties(order, persistentOrder.get().getOrderId());
			orderRepository.save(persistentOrder.get());

		return order;

	}

	private Order defineAddress(Order order) {
		for (int i = 0; i < order.getAddress().size(); i++) {
			List<Address> addresses = addressRepository.findByStoreIdAndCityIgnoreCaseAndZip(order.getOrderId(),
					order.getAddress().get(i).getCity(), order.getAddress().get(i).getZip());

			if (!addresses.isEmpty()) {
				order.getAddress().get(i).setAddressId(addresses.get(i).getAddressId());
				order.getAddress().get(i).setOrderId(order.getOrderId());
			}
		}
		return order;
	}
}
