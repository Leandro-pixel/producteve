package com.producteve.gerenciadordeprodutos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.producteve.gerenciadordeprodutos.controller.ProductDto;
import com.producteve.gerenciadordeprodutos.controller.ProductListResponseDTO;
import com.producteve.gerenciadordeprodutos.entity.Product;
import com.producteve.gerenciadordeprodutos.repository.ProductRepository;

//import java.util.List;
//import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    
    public Product create(ProductDto dto) {
        
            Product product = new Product(
                    dto.getUserId(),
                    dto.getName(),
                    dto.getValue(),
                    dto.getEstimateWasteDays(),
                    dto.getLastPurchaseDate(),
                    dto.getCalendarEventId()
            );
            calculateReminder(product);

        return productRepository.save(product);
        }
    
        public Optional<Product> update(Long id, ProductDto dto) {
            return productRepository.findById(id).map(existing -> {
                existing.setUserId(dto.getUserId());
                existing.setName(dto.getName());
                existing.setValue(dto.getValue());
                existing.setEstimateWasteDays(dto.getEstimateWasteDays());
                existing.setLastPurchaseDate(dto.getLastPurchaseDate());
                existing.setCalendarEventId(dto.getCalendarEventId());
                return productRepository.save(existing);
            });
        }
    
        public void delete(Long id) {
            productRepository.deleteById(id);
        }

         public ProductListResponseDTO listAll() {
        List<Product> products = productRepository.findAll();
        return new ProductListResponseDTO(products.size(), products);
    }

    public ProductListResponseDTO listByUserId(UUID userId) {
    List<Product> products = productRepository.findByUserId(userId);
    return new ProductListResponseDTO(products.size(), products);
}

private void calculateReminder(Product product) {
    // Obtém o valor da última compra e o número de dias estimado
    LocalDate lastPurchaseDate = product.getLastPurchaseDate();
    int estimateWasteDays = product.getEstimateWasteDays();

    // Calcula a data de lembrete (a data em que o produto estará acabando)
    LocalDate reminderDate = lastPurchaseDate.plusDays(estimateWasteDays);
    
    // Convertendo o LocalDate (que não tem hora) para LocalDateTime com a hora fixada para meio-dia (12:00)
    LocalDate reminderDateTime = reminderDate; // Meio-dia

    // Definindo o lembrete com o horário completo (LocalDateTime)
    product.setReminderDate(reminderDateTime);

    // Definindo o valor do produto
    product.setValue(product.getValue());
}


    
}
