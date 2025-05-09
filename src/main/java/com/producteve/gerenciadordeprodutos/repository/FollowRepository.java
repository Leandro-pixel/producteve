package com.producteve.gerenciadordeprodutos.repository;

import com.producteve.gerenciadordeprodutos.entity.Follow;
import com.producteve.gerenciadordeprodutos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {
    boolean existsByFollowerAndFollowed(User follower, User followed);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowed(User followed);
    List<Follow> findByFollowedUserId(UUID followedUserId);
}
