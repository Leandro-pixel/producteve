package com.producteve.gerenciadordeprodutos.controller;
import org.springframework.web.bind.annotation.*;

import com.producteve.gerenciadordeprodutos.dto.FollowRequest;
import com.producteve.gerenciadordeprodutos.service.FollowService;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/v1/follows")
@CrossOrigin(origins = "*")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
public ResponseEntity<String> follow(@RequestBody FollowRequest request) {
    boolean success = followService.followUser(request.getFollowerId(), request.getFollowedId());

    if (success) {
        return ResponseEntity.ok("Agora você está seguindo esse usuário.");
    } else {
        return ResponseEntity.badRequest().body("Não foi possível seguir o usuário (já está seguindo ou IDs inválidos).");
    }
}

}
