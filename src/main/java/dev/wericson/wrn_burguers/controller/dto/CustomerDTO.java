package dev.wericson.wrn_burguers.controller.dto;

import java.util.List;

import dev.wericson.wrn_burguers.domain.model.Customer;

public record CustomerDTO(
		Long id,
		String name,
		List<OrderDTO> orders
		){

    public CustomerDTO(Customer model) {
    	this(
    			model.getId(),
    			model.getName(),
    			model.getOrders().stream().map(OrderDTO::new).toList()
    	);
    }
    
    
    public Customer tomodel() {
    	Customer model = new Customer();
    	model.setId(this.id);
    	model.setName(this.name);
    	model.setOrders(this.orders.stream().map(OrderDTO::toModel).toList());
    	
    	return model;
    }
}

