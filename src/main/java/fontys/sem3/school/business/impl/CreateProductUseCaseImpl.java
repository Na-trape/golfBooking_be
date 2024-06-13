package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CreateProductUseCase;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public CreateProductResponse createProduct(CreateProductRequest request) {
        ProductEntity newProduct = ProductEntity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();

        ProductEntity savedProduct = productRepository.save(newProduct);

        return CreateProductResponse.builder()
                .productId(savedProduct.getId())
                .build();
    }
}
