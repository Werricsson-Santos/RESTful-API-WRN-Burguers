package dev.wericson.wrn_burguers.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.wericson.wrn_burguers.domain.model.Product;
import dev.wericson.wrn_burguers.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Product>> findAll() {
		List<Product> products = this.productService.findAll();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@Parameter(description = "Product ID") @PathVariable(value = "id") Long id) {
		var product = this.productService.findById(id);
		return ResponseEntity.ok(product);
	}

	@PostMapping
	public ResponseEntity<Product> create(@RequestBody Product productToCreate) {	
		var productCreated = this.productService.create(productToCreate);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(productCreated.getId())
						.toUri();
		return ResponseEntity.created(location).body(productCreated);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable(value = "id") Long id, @RequestBody Product productToUpdate) {
		var product = this.productService.update(id, productToUpdate);
		
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		this.productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
