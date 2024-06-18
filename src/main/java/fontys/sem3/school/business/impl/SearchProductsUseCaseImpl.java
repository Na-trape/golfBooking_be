package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.SearchProductsUseCase;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchProductsUseCaseImpl implements SearchProductsUseCase {
    private final ProductRepository productRepository;

    @Override
    public List<Product> searchProducts(String name, String category, String sort) {
        List<ProductEntity> productEntities = productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseWithSort(name, category, sort);
        return productEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
