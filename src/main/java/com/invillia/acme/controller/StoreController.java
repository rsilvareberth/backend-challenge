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
import com.invillia.acme.model.Store;
import com.invillia.acme.model.StoreRepository;

@Component
public class StoreController {

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	AddressRepository addressRepository;

	public List<Store> findAll() {
		return storeRepository.findAll();
	}
	
	public Optional<Store> findById(Long id) {
		return storeRepository.findById(id);
	}

	public List<Store> findByName(String name) {
		List<Store> stores = storeRepository.findByNameIgnoreCase(name);

		for (int i = 0; i < stores.size(); i++) {
			stores.get(i).getAddress().size();
		}
		return stores;

	}

	public List<Store> findByZipAndCity(String zip, String city) {
		return storeRepository.findByZipAndCity(zip, city);
	}

	public Store updateStore(Store store) throws IOException {

		List<Store> persistentStore = storeRepository.findByNameIgnoreCase(store.getName());

		if (!persistentStore.isEmpty()) {

			store.setStoreId(persistentStore.get(0).getStoreId());
			store = defineAddress(store);

			org.springframework.beans.BeanUtils.copyProperties(store, persistentStore.get(0));
			storeRepository.save(persistentStore.get(0));

		}
		return store;

	}

	private Store defineAddress(Store store) {
		for (int i = 0; i < store.getAddress().size(); i++) {
			List<Address> addresses = addressRepository.findByStoreIdAndCityIgnoreCaseAndZip(store.getStoreId(),
					store.getAddress().get(i).getCity(), store.getAddress().get(i).getZip());

			if (!addresses.isEmpty()) {
				store.getAddress().get(i).setAddressId(addresses.get(i).getAddressId());
				store.getAddress().get(i).setStoreId(store.getStoreId());
			}
		}
		return store;
	}
}
