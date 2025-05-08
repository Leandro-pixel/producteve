package com.producteve.gerenciadordeprodutos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.producteve.gerenciadordeprodutos.entity.LoginRequest;
import com.producteve.gerenciadordeprodutos.entity.LoginResponse;
import com.producteve.gerenciadordeprodutos.entity.User;
import com.producteve.gerenciadordeprodutos.service.UserService;

//import app.com.producteve.entity.LoginResponse;


import java.net.URI;
//import java.util.List;
//import java.util.List;
//import java.util.Optional;
import java.util.List;
import java.util.Optional;

@RestController //com isso aqui o springboot entende que tudo que eu definir aqui é um endpoint da API
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
    Optional<User> existingUser = userService.authenticateUser(createUserDto.email());

    if (existingUser.isPresent()) {
        return ResponseEntity.status(409).body("E-mail já cadastrado");
    }

    User user = userService.createUser(createUserDto);
    return ResponseEntity.created(URI.create("/v1/users/" + user.getUserId())).body(user);
}



    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.authenticateUser(loginRequest.email());
    
        if (user.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(user.get().getUserId().toString()));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Credenciais inválidas"));
        }
    }

    @GetMapping("/search/userName")
public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam String username) {
    List<User> users = userService.searchUsersByUsername(username);
    return ResponseEntity.ok(users);
}

    
 /* 
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody CreateUserDto createUserDto) {
        // Verifica se o usuário existe e as credenciais estão corretas
        Optional<User> user = userService.authenticateUser(createUserDto.email(), createUserDto.password());
    
        if (user.isPresent()) {
            // Cria o objeto de resposta com o UUID
            LoginResponse loginResponse = new LoginResponse(user.get().getUserId().toString());
            return ResponseEntity.ok(loginResponse); // Retorna a resposta no formato JSON
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Credenciais inválidas"));
        }
    }
    

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
        */
}