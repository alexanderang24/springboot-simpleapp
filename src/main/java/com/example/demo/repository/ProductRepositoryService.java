package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRepositoryService {

    @Autowired
    ProductRepository repository;

    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public void save(Product product) {
        repository.save(product);
    }
}
