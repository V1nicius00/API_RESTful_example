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
        List<Product> allProducts = productService.findAll();
        if(allProducts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for(Product product : allProducts){
            String id = product.getId();
            product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        }
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable(value="id") String id){
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductDTO data) {
        Product newProduct = new Product(data);
        productService.save(newProduct);
        return ResponseEntity.ok("Produto criado com sucesso!");
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
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable String id){
        return productService.delete(id);
    }

}
