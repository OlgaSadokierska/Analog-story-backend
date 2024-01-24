package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "camera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String model;

    private String brand;

    @Column(name = "film_loaded")
    private Boolean filmLoaded;

    @Getter
    @Column(name = "is_for_sale")
    private Boolean isForSale;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product ;

    public Boolean getIsForSale() {
        return isForSale;
    }

}
