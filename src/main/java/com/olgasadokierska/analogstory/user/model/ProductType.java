package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameTypeProduct", nullable = false, unique = true)
    private String typeName;

}
