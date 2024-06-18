package dev.wericson.wrn_burguers.service.impl;

import java.util.List;

import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.domain.repository.CustomerRepository;
import dev.wericson.wrn_burguers.domain.repository.OrderRepository;
import dev.wericson.wrn_burguers.service.OrderService;
import dev.wericson.wrn_burguers.service.exception.NotFoundException;

public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<Order> findAll() {
		return this.orderRepository.findAll();
	}

	@Override
	public Order findById(Long id) {
		return this.orderRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public Order create(Order orderToCreate) {
		ofNullable(orderToCreate.getCustomer().getId()).orElseThrow(() -> new BusinessException("To make an order is necessary 1 customer ID."));
		ofNullable(orderToCreate.getProducts()).orElseThrow(() -> new BusinessException("To make an order is necessary add at least 1 product."));
		
		if()
		return null;
	}

	@Override
	public Order update(Long id, Order entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
