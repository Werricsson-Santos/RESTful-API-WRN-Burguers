package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.domain.repository.CustomerRepository;
import dev.wericson.wrn_burguers.service.CustomerService;
import dev.wericson.wrn_burguers.service.exception.BusinessException;
import dev.wericson.wrn_burguers.service.exception.NotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		return this.customerRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	@Transactional
	public Customer create(Customer customerToCreate) {
		ofNullable(customerToCreate.getName()).orElseThrow(() -> new BusinessException("Customer to create must have a name."));
		ofNullable(customerToCreate.getCPF()).orElseThrow(() -> new BusinessException("Customer to create must have an CPF."));

		if(customerRepository.existsByCpf(customerToCreate.getCPF())) {
			throw new BusinessException("This CPF number already exists. Customer to create must have an unique CPF.");
		}

		return this.customerRepository.save(customerToCreate);
	}

	@Override
	@Transactional
	public Customer update(Long id, Customer customerToUpdate) {
		Customer dbCustomer = this.findById(id);
		customerToUpdate.setId(id);
		if (!dbCustomer.getId().equals(customerToUpdate.getId())) {
			throw new BusinessException("Update IDs must be the same.");
		}

		if(customerToUpdate.getName() != null) 
			dbCustomer.setName(customerToUpdate.getName());
		if(customerToUpdate.getCPF() != null)
			dbCustomer.setCPF(customerToUpdate.getCPF());
		dbCustomer.setOrders(customerToUpdate.getOrders());
		return this.customerRepository.save(dbCustomer);
	}
	
	public Order makeOrder(Long id, Customer customerToMakeOrder) {
		Customer dbCustomer = this.findById(id);
		customerToMakeOrder.setId(id);
		
		if(customerToMakeOrder.getOrders() != null) {
			Order newOrder = Order(customerToMakeOrder.getOrders().stream().findFirst().orElseThrow());
			dbCustomer.setOrders(null);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Customer dbCustomer = this.findById(id);
		this.customerRepository.delete(dbCustomer);
	}

}
