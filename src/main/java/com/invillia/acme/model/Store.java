package com.invillia.acme.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "store")
@Table(name = "store", uniqueConstraints = @UniqueConstraint(columnNames = "name", name = "unique_name"))

public class Store {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storeId")
	private Long storeId;

	@OneToMany(cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "storeId")
	private List<Address> address = new ArrayList<>();

	@Column(nullable = false)
	protected String name;

	public Long getStoreId() {
		return storeId;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public void setStoreId(Long id) {
		this.storeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
