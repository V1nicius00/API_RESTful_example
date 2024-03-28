package com.apiproducts.products.controllers;

import com.apiproducts.products.models.Product;
import com.apiproducts.products.dtos.ProductDTO;
import com.apiproducts.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable(value="id") String id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductDTO data) {
        return productService.save(data);
    }

    @PutMapping
    @Transactional
    public ResponseEntity putProduct(@RequestBody ProductDTO data) {
        return productService.update(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable(value="id") String id){
        return productService.delete(id);
    }

}
