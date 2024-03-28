package com.apiproducts.products.services;

import com.apiproducts.products.controllers.ProductController;
import com.apiproducts.products.models.Product;
import com.apiproducts.products.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity delete(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            productRepository.delete(product);
            return ResponseEntity.ok("Produto deletado com sucesso!");
        }
        return ResponseEntity.notFound().build();
    }
}
