package com.apiproducts.products.services;

import com.apiproducts.products.controllers.ProductController;
import com.apiproducts.products.dtos.ProductDTO;
import com.apiproducts.products.models.Product;
import com.apiproducts.products.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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
    public ResponseEntity save(ProductDTO data) {
        Product newProduct = new Product(data);
        productRepository.save(newProduct);
        return ResponseEntity.ok("Produto criado com sucesso!");
    }

    public ResponseEntity findAll() {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for(Product product : allProducts){
            String id = product.getId();
            product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        }
        return ResponseEntity.ok(allProducts);
    }

    private Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public ResponseEntity getProductById(String id){
        Optional<Product> product = this.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.ok(product);
    }

    @Transactional
    public ResponseEntity update(ProductDTO data){
        Optional<Product> optionalProduct = this.findById(data.id());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok("Produto atualizado!");
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity delete(String id) {
        Optional<Product> optionalProduct = this.findById(id);
        if(optionalProduct.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        productRepository.delete(product);
        return ResponseEntity.ok("Produto deletado com sucesso!");
    }

}
