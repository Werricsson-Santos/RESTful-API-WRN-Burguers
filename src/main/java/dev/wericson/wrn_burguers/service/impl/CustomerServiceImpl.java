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
	
	/**
     * ID de cliente com o meu nome.
     * Por isso, vamos criar algumas regras para mantê-lo integro.
     */
    private static final Long UNCHANGEABLE_CUSTOMER_ID = 1L;

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
		ofNullable(customerToCreate.getName()).orElseThrow(() -> new BusinessException("Customer to create must have a Name."));
		ofNullable(customerToCreate.getCPF()).orElseThrow(() -> new BusinessException("Customer to create must have an CPF."));
		
		this.validateChangeableId(customerToCreate.getId(), "created");

		if(customerRepository.existsByCpf(customerToCreate.getCPF())) {
			throw new BusinessException("This CPF number already exists. Customer to create must have an unique CPF.");
		}

		return this.customerRepository.save(customerToCreate);
	}

	@Override
	@Transactional
	public Customer update(Long id, Customer customerToUpdate) {
		this.validateChangeableId(id, "updated");
		Customer dbCustomer = this.findById(id);
		customerToUpdate.setId(id);
		if (!dbCustomer.getId().equals(customerToUpdate.getId())) {
			throw new BusinessException("Update IDs must be the same.");
		}

		if(customerToUpdate.getName() != null) 
			dbCustomer.setName(customerToUpdate.getName());
		if(customerToUpdate.getCPF() != null)
			dbCustomer.setCPF(customerToUpdate.getCPF());
		if(customerToUpdate.getOrders() != null && !customerToUpdate.getOrders().isEmpty())
			dbCustomer.setOrders(customerToUpdate.getOrders());
		return this.customerRepository.save(dbCustomer);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.validateChangeableId(id, "deleted");
		Customer dbCustomer = this.findById(id);
		this.customerRepository.delete(dbCustomer);
	}
	
	private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_CUSTOMER_ID.equals(id)) {
            throw new BusinessException("Customer with ID %d can not be %s.".formatted(UNCHANGEABLE_CUSTOMER_ID, operation));
        }
    }

}
