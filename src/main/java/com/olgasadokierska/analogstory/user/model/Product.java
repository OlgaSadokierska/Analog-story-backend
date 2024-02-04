package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    private String description;

    private double price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String model;

    private String brand;

}
