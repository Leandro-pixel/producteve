package com.producteve.gerenciadordeprodutos.controller;

//import com.producteve.gerenciadordeprodutos.controller.ProductDto;
//import com.producteve.gerenciadordeprodutos.controller.ProductListResponseDTO;
import com.producteve.gerenciadordeprodutos.entity.Product;
import com.producteve.gerenciadordeprodutos.service.ProductService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDto dto) {
        Product created = service.create(dto);
        return ResponseEntity.ok(created);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ProductListResponseDTO> listByUserId(@RequestParam UUID userId) {
        return ResponseEntity.ok(service.listByUserId(userId));
    }

    @GetMapping("/global")
    public ResponseEntity<List<Product>> listGlobalProducts() {
    return ResponseEntity.ok(service.listGlobalProducts());
}

@PostMapping("/copy-to-personal/{id}")
public ResponseEntity<Product> copyGlobalToPersonal(@PathVariable Long id, @RequestParam UUID userId) {
    return ResponseEntity.ok(service.copyGlobalProductToUser(id, userId));
}

@PostMapping("/comments/{id}")
public ResponseEntity<Product> addComment(
        @PathVariable Long id,
        @RequestBody CommentDto dto) {
    return ResponseEntity.ok(service.addComment(id, dto));
}

    
}
