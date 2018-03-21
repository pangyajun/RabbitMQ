package com.oaec.entity;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3943229866065284308L;
	private String id;
	private String name;
	private Double price;
	private List<Spec> listSpec;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Product() {
		super();
	}
	public List<Spec> getListSpec() {
		return listSpec;
	}
	public void setListSpec(List<Spec> listSpec) {
		this.listSpec = listSpec;
	}
	public Product(String id, String name, Double price, List<Spec> listSpec) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.listSpec = listSpec;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", listSpec=" + listSpec + "]";
	}
	
	
	
	
}
