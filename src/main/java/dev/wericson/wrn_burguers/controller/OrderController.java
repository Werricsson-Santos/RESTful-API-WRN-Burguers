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

import dev.wericson.wrn_burguers.controller.dto.OrderDTO;
import dev.wericson.wrn_burguers.domain.model.Order;
import dev.wericson.wrn_burguers.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/")
	public ResponseEntity<List<OrderDTO>> findAll() {
		var orders = orderService.findAll();
		var orderDto = orders.stream().map(OrderDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok(orderDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findById(@Parameter(description = "order ID", required = true) @PathVariable(value = "id", required = true) Long id) {
		var order = this.orderService.findById(id);
		return ResponseEntity.ok(new OrderDTO(order));
	}

	@PostMapping
	public ResponseEntity<OrderDTO> create(@RequestBody Order orderToCreate) {
		var orderCreated = this.orderService.create(orderToCreate);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(orderCreated.getId())
						.toUri();
		return ResponseEntity.created(location).body(new OrderDTO(orderCreated));
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable(value = "id") Long id, @RequestBody Order orderToUpdate) {
        var order = this.orderService.update(id, orderToUpdate);
        return ResponseEntity.ok(order);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        this.orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
