package fontys.sem3.school.unit;

import fontys.sem3.school.business.impl.CreateProductUseCaseImpl;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class CreateProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;

    @Test
    void createProduct_Success() {
        CreateProductRequest request = CreateProductRequest.builder()
                .name("Product 1")
                .price(new BigDecimal("10.00"))
                .category("Category 1")
                .description("Description 1")
                .build();

        when(productRepository.save(any(ProductEntity.class))).thenAnswer(invocation -> {
            ProductEntity productEntity = invocation.getArgument(0);
            productEntity.setId(1L);
            return productEntity;
        });

        CreateProductResponse response = createProductUseCase.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.getProductId());
    }
}
