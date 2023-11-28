package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "id_camera")
    private Long idCamera;

    @Column(name = "loaded_frames")
    private Integer loadedFrames;

    @Column(name = "is_full")
    private Boolean isFull;

    @ManyToOne
    @JoinColumn(name = "id_produktu")
    private Product product;

    @Column(name = "is_for_sale")
    private Boolean isForSale;

    public void setIdCamera(Long idCamera) {
        this.idCamera = idCamera;
    }
    public Long getIdCamera() {
        return idCamera;
    }

}