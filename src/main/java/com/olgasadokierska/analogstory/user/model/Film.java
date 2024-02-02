package com.olgasadokierska.analogstory.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "film")
@JsonIgnoreProperties({"product"})
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_camera", nullable = true)
    private Long idCamera;

    @Column(name = "loaded_frames")
    private Integer loadedFrames;

    @Column(name = "is_full")
    private Boolean isFull;

    @ManyToOne
    @JoinColumn(name = "id_produktu")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Column(name = "is_for_sale")
    private Boolean isForSale;

    @Column(name = "max_loaded")
    private Integer maxLoaded;


    public void setIdCamera(Long idCamera) {
        this.idCamera = idCamera;
    }
    public Long getIdCamera() {
        return idCamera;
    }

    public Boolean getIsForSale() {
        return isForSale;
    }
}
