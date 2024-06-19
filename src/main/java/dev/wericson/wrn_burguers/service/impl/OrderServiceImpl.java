package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.domain.model.Product;
import dev.wericson.wrn_burguers.domain.repository.CustomerRepository;
import dev.wericson.wrn_burguers.domain.repository.OrderRepository;
import dev.wericson.wrn_burguers.service.OrderService;
import dev.wericson.wrn_burguers.service.exception.BusinessException;
import dev.wericson.wrn_burguers.service.exception.NotFoundException;

@Service
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
		
		if(!customerRepository.existsById(orderToCreate.getCustomer().getId())) {
			throw new BusinessException("Customer not found. To make an order you must have specify a customer by ID.");
		}
		var bdCustomer = customerRepository.findById(orderToCreate.getCustomer().getId());
		List<Order> customerOrders = bdCustomer.get().getOrders();
		
		if(orderToCreate.getTotalAmount() == null)
			orderToCreate.setTotalAmount(orderToCreate.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		customerOrders.add(orderToCreate);
		
		return this.orderRepository.save(orderToCreate);
	}

	@Override
	public Order update(Long id, Order orderToUpdate) {
		Order bdOrder = this.orderRepository.findById(id).orElseThrow(NotFoundException::new);
		orderToUpdate.setId(bdOrder.getId());
		
		if(orderToUpdate.getProducts().isEmpty()) {
			throw new BusinessException("Order to update must have at least 1 product.");
		}
		
		bdOrder.setProducts(orderToUpdate.getProducts());
		bdOrder.setTotalAmount(orderToUpdate.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		
		if(bdOrder.isPaid() != orderToUpdate.isPaid())
			bdOrder.setPaid(orderToUpdate.isPaid());
		
		if(bdOrder.isDelivered() != orderToUpdate.isDelivered())
			bdOrder.setDelivered(orderToUpdate.isDelivered());
		
		if(bdOrder.isOrderCompleted() != orderToUpdate.isOrderCompleted())
			bdOrder.setOrderCompleted(orderToUpdate.isOrderCompleted());
		
		return this.orderRepository.save(bdOrder);
	}

	@Override
	public void delete(Long id) {
		Order bdOrder = this.orderRepository.findById(id).orElseThrow(NotFoundException::new);
		Customer bdCustomer = bdOrder.getCustomer();
		
		if(bdCustomer != null) {
			bdCustomer.removeOrder(bdOrder);
			bdOrder.setCustomer(null);
			customerRepository.save(bdCustomer);
		}
		
		bdOrder.setProducts(null);
		this.orderRepository.deleteById(id);
	}

}
