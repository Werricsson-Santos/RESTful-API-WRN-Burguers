package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.wericson.wrn_burguers.domain.model.Product;
import dev.wericson.wrn_burguers.domain.repository.ProductRepository;
import dev.wericson.wrn_burguers.service.ProductService;
import dev.wericson.wrn_burguers.service.exception.BusinessException;
import dev.wericson.wrn_burguers.service.exception.NotFoundException;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}

	@Override
	public Product findById(Long id) {
		return this.productRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public Product create(Product productToCreate) {
		ofNullable(productToCreate.getName()).orElseThrow(() -> new BusinessException("Product to create must have a name."));
		ofNullable(productToCreate.getPrice()).orElseThrow(() -> new BusinessException("Product to create must have a price."));
		return null;
	}

	@Override
	public Product update(Long id, Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
