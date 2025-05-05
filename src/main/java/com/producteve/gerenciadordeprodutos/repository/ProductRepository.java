package com.producteve.gerenciadordeprodutos.repository;
import org.springframework.stereotype.Repository;

import com.producteve.gerenciadordeprodutos.entity.Product;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(UUID userId);
}
