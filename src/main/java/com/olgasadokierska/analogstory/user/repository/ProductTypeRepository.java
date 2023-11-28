package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}