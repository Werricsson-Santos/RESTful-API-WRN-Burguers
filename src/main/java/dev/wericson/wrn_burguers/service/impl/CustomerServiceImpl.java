package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.domain.repository.CustomerRepository;
import dev.wericson.wrn_burguers.dto.CustomerDTO;
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
		ofNullable(customerToCreate).orElseThrow(() -> new BusinessException("Customer to create must have an CPF."));

		if(customerRepository.existsByCpf(customerToCreate.getCPF())) {
			throw new BusinessException("This CPF number already exists. Customer to create must have an unique CPF.");
		}

		return this.customerRepository.save(customerToCreate);
	}

	@Override
	@Transactional
	public Customer update(Long id, Customer customerToUpdate) {
		Customer dbCustomer = this.findById(id);
		if (!dbCustomer.getId().equals(customerToUpdate.getId())) {
			throw new BusinessException("Update IDs must be the same.");
		}

		dbCustomer.setName(customerToUpdate.getName());
		dbCustomer.setCPF(customerToUpdate.getCPF());
		dbCustomer.setOrders(customerToUpdate.getOrders());
		return this.customerRepository.save(dbCustomer);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Customer dbCustomer = this.findById(id);
		this.customerRepository.delete(dbCustomer);
	}

	@Override
	public CustomerDTO convertToDTO(Customer customerToConvert) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customerToConvert.getId());
		customerDTO.setName(customerToConvert.getName());
		customerDTO.setOrders(customerToConvert.getOrders());

		return customerDTO;
	}

}
