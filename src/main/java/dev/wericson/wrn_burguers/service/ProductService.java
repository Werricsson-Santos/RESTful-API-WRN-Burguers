package dev.wericson.wrn_burguers.service;

import org.springframework.stereotype.Service;

import dev.wericson.wrn_burguers.domain.model.Product;

@Service
public interface ProductService extends CrudService<Long, Product> {

}
