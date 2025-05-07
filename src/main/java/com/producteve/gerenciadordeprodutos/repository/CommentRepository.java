package com.producteve.gerenciadordeprodutos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.producteve.gerenciadordeprodutos.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
