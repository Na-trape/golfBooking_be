package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.GetProductByIdUseCase;
import fontys.sem3.school.business.exception.ProductNotFoundException;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final ProductRepository productRepository;

    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return productEntity.map(this::convertToDTO);
    }

    private Product convertToDTO(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .category(entity.getCategory())
                .description(entity.getDescription())
                .build();
    }
}
