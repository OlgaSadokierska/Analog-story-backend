package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
