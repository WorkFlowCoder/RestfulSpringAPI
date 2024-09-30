package com.api.apiBackEnd.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.apiBackEnd.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
