package com.producteve.gerenciadordeprodutos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.producteve.gerenciadordeprodutos.controller.CommentDto;
import com.producteve.gerenciadordeprodutos.controller.ProductDto;
import com.producteve.gerenciadordeprodutos.controller.ProductListResponseDTO;
import com.producteve.gerenciadordeprodutos.entity.Comment;
import com.producteve.gerenciadordeprodutos.entity.Product;
import com.producteve.gerenciadordeprodutos.entity.User;
import com.producteve.gerenciadordeprodutos.repository.ProductRepository;
import com.producteve.gerenciadordeprodutos.repository.UserRepository;

//import java.util.List;
//import java.util.UUID;

@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository,UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;

    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    
    public Product create(ProductDto dto) {
        // Busca o nome do usuário pelo ID
        String userName = userRepository.findById(dto.getUserId())
            .map(User::getUsername)
            .orElse("Desconhecido"); // Ou pode lançar uma exceção se preferir

        Product product = new Product(
                dto.getUserId(),
                dto.getName(),
                dto.getValue(),
                dto.getEstimateWasteDays(),
                dto.getLastPurchaseDate(),
                dto.getCalendarEventId()
        );

        product.setAddedBy(userName);
        product.setGlobalStock(true);
        product.setUsers(1);

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

        public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
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

public List<Product> listGlobalProducts() {
    return productRepository.findByGlobalStockTrue();
}

public Product copyGlobalProductToUser(Long globalProductId, UUID userId) {
    Product globalProduct = productRepository.findById(globalProductId)
        .orElseThrow(() -> new RuntimeException("Produto global não encontrado"));

    if (!globalProduct.isGlobalStock()) {
        throw new RuntimeException("Produto não é global");
    }

    Product personalProduct = new Product();
    personalProduct.setUserId(userId);
    personalProduct.setName(globalProduct.getName());
    personalProduct.setValue(globalProduct.getValue());
    personalProduct.setEstimateWasteDays(globalProduct.getEstimateWasteDays());
    personalProduct.setLastPurchaseDate(globalProduct.getLastPurchaseDate());
    personalProduct.setCalendarEventId(null); // novo evento para o usuário
    personalProduct.setReminderDate(globalProduct.getReminderDate());
    personalProduct.setGlobalStock(false);
    personalProduct.setActiveStatus(true);

    return productRepository.save(personalProduct);
}

public Product addComment(Long productId, CommentDto dto) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    Comment comment = new Comment();
    comment.setUserId(dto.getUserId());
    comment.setText(dto.getText());

    // Buscando o nome do usuário
    User user = userRepository.findById(dto.getUserId()).orElse(null);

    // Atribuindo o nome do usuário ao comentário
    String userName = (user != null) ? user.getUsername() : "Desconhecido"; // Se o usuário não for encontrado, o nome será 'Desconhecido'

    comment.setUserName(userName);
    comment.setProduct(product); // Relacionando o comentário ao produto

    product.getComments().add(comment); // Adicionando o comentário à lista de comentários do produto
System.out.println("UUID recebido: " + dto.getUserId());
Optional<User> userOpt = userRepository.findById(dto.getUserId());

if (userOpt.isPresent()) {
    System.out.println("Usuário encontrado: " + userOpt.get().getUsername());
} else {
    System.out.println("Usuário NÃO encontrado no banco.");
}

    return productRepository.save(product); // Salvando o produto com o novo comentário
}




}
