package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.wericson.wrn_burguers.domain.model.Customer;
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
	public Customer create(Customer customerToCreate) {
		ofNullable(customerToCreate).orElseThrow(() -> new BusinessException("Customer to create must have an CPF."));
		
		if(customerRepository.existsByCpfNumber(customerToCreate.getCPF())) {
			throw new BusinessException("This CPF number already exists. Customer to create must have an unique CPF.");
		}
		
		return this.customerRepository.save(customerToCreate);
	}

	@Override
	public Customer update(Long id, Customer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
