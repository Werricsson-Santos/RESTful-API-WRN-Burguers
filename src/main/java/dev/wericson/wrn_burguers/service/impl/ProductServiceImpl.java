package dev.wericson.wrn_burguers.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return this.productRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	@Transactional
	public Product create(Product productToCreate) {
		ofNullable(productToCreate.getName()).orElseThrow(() -> new BusinessException("Product to create must have a name."));
		ofNullable(productToCreate.getPrice()).orElseThrow(() -> new BusinessException("Product to create must have a price."));
		
		return this.productRepository.save(productToCreate);
	}

	@Override
	@Transactional
	public Product update(Long id, Product productToUpdate) {
		Product dbProduct = this.findById(id);
		if (!dbProduct.getId().equals(productToUpdate.getId())) {
			throw new BusinessException("Update IDs must be the same.");
		}
		
		dbProduct.setName(productToUpdate.getName());
		dbProduct.setPrice(productToUpdate.getPrice());
		return this.productRepository.save(dbProduct);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Product dbProduct = this.findById(id);
		this.productRepository.delete(dbProduct);
	}

}
