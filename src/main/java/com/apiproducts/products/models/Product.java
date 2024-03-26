package com.apiproducts.products.models;

import com.apiproducts.products.dtos.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product extends RepresentationModel<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer price_in_cents;

    public Product(ProductDTO dto){
        this.name = dto.name();
        this.price_in_cents = dto.price_in_cents();
    }
}
