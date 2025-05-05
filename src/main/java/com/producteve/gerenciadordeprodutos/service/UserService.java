package com.producteve.gerenciadordeprodutos.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.producteve.gerenciadordeprodutos.controller.CreateUserDto;
import com.producteve.gerenciadordeprodutos.entity.User;
import com.producteve.gerenciadordeprodutos.repository.UserRepository;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(CreateUserDto createUserDto) {
        Optional<User> existingUser = userRepository.findByEmail(createUserDto.email());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com esse e-mail.");
        }
    
        var entity = new User(
            null,
            createUserDto.username(),
            createUserDto.email(),
            Instant.now(),
            null
        );
    
        return userRepository.save(entity);
    }
    
    
        
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
        
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> authenticateUser(String email) {
        return userRepository.findByEmail(email);
    }
    
    
    /* 

    public void updateUserById(String userId,UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }

    }
*/
    public void deleteById(String userId) {

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
           
    }
        
}
