package dev.wericson.wrn_burguers.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.domain.model.Product;

public record OrderDTO(
		Long id,
		Long customerId,
		BigDecimal totalAmount,
		List<Product> products,
		boolean delivered,
		boolean paid,
		boolean orderCompleted
		) {
	
	public OrderDTO(Order model) {
		this (
				model.getId(),
				model.getCustomer().getId(),
				model.getTotalAmount(),
				model.getProducts(),
				model.isDelivered(),
				model.isPaid(),
				model.isOrderCompleted()
		);
	}

	public Order toModel() {
		Order model = new Order();
		model.setId(this.id);
		model.setTotalAmount(this.totalAmount);
		model.setProducts(this.products);
		model.setDelivered(this.delivered);
		model.setPaid(this.paid);
		model.setOrderCompleted(this.orderCompleted);
		
		return model;
	}
}
