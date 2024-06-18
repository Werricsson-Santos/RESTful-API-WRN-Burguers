package dev.wericson.wrn_burguers.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.wericson.wrn_burguers.controller.dto.CustomerDTO;
import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.service.CustomerService;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin
@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/")
	public ResponseEntity<List<CustomerDTO>> findAll() {
		var customers = customerService.findAll();
		var customersDto = customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok(customersDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> findById(@Parameter(description = "Customer ID", required = true) @PathVariable(value = "id", required = true) Long id) {
		var customer = this.customerService.findById(id);
		return ResponseEntity.ok(new CustomerDTO(customer));
	}

	@PostMapping
	public ResponseEntity<CustomerDTO> create(@RequestBody Customer customerToCreate) {
		if(customerToCreate.getOrders() != null && !customerToCreate.getOrders().isEmpty()) {
			List<Order> orders = customerToCreate.getOrders();
			orders.stream().forEach(order -> order.setCustomer(customerToCreate));
		}
		
		var customerCreated = this.customerService.create(customerToCreate);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(customerCreated.getId())
						.toUri();
		return ResponseEntity.created(location).body(new CustomerDTO(customerCreated));
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable(value = "id") Long id, @RequestBody Customer customerToUpdate) {
        var customer = this.customerService.update(id, customerToUpdate);
        return ResponseEntity.ok(customer);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        this.customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
