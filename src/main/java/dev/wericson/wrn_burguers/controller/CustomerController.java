package dev.wericson.wrn_burguers.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.wericson.wrn_burguers.domain.model.Customer;
import dev.wericson.wrn_burguers.dto.CustomerDTO;
import dev.wericson.wrn_burguers.service.CustomerService;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/")
	public ResponseEntity<List<CustomerDTO>> findAll() {
		List<CustomerDTO> customers = this.customerService.findAll().stream().map(customer -> customerService.convertToDTO(customer)).toList();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> findById(@Parameter(required = true) @PathVariable Long id) {
		var customer = this.customerService.convertToDTO(this.customerService.findById(id));
		return ResponseEntity.ok(customer);
	}

	@PostMapping
	public ResponseEntity<Customer> create(@RequestBody Customer customerToCreate) {	
		var customerCreated = this.customerService.create(customerToCreate);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(customerCreated.getId())
						.toUri();
		return ResponseEntity.created(location).body(customerCreated);
	}
}
