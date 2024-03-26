package com.apiproducts.products.services;

import com.apiproducts.products.models.Product;
import com.apiproducts.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public Product save(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
