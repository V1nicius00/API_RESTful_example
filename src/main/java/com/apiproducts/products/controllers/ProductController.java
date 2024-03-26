package com.apiproducts.products.controllers;

import com.apiproducts.products.models.Product;
import com.apiproducts.products.dtos.ProductDTO;
import com.apiproducts.products.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<Product> allProducts = productService.findAll();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductDTO data) {
        Product newProduct = new Product(data);
        productService.save(newProduct);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity putProduct(@RequestBody ProductDTO data) {
        Optional<Product> optionalProduct = productService.findById(data.id());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok("Produto atualizado!");
        }
        throw new EntityNotFoundException("Produto não encontrado!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable String id){
        Optional<Product> optionalProduct = productService.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            productService.delete(product);
            return ResponseEntity.ok("Produto deletado com sucesso!");
        }
        throw new EntityNotFoundException("Erro ao deletar produto!");
    }

}
