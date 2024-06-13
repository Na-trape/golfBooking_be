package fontys.sem3.school.unit;

import fontys.sem3.school.business.exception.InvalidProductException;
import fontys.sem3.school.business.impl.UpdateProductUseCaseImpl;
import fontys.sem3.school.domain.ProductRequestResponse.UpdateProductRequest;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCase;

    @BeforeEach
    void setUp() {
        // Initialize the mocks and use case implementation
        updateProductUseCase = new UpdateProductUseCaseImpl(productRepository);
    }

    @Test
    void updateProduct_Success() {
        UpdateProductRequest request = UpdateProductRequest.builder()
                .id(1L)
                .name("Updated Golf Club Set")
                .price(new BigDecimal("599.99"))
                .category("Golf Equipment")
                .description("An updated complete set of golf clubs for all skill levels.")
                .build();

        ProductEntity existingProduct = ProductEntity.builder()
                .id(1L)
                .name("Golf Club Set")
                .price(new BigDecimal("499.99"))
                .category("Golf Equipment")
                .description("A complete set of golf clubs for all skill levels.")
                .build();

        when(productRepository.findById(request.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(existingProduct);

        assertDoesNotThrow(() -> updateProductUseCase.updateProduct(request));

        verify(productRepository).findById(request.getId());
        verify(productRepository).save(existingProduct);

        assertEquals(request.getName(), existingProduct.getName());
        assertEquals(request.getPrice(), existingProduct.getPrice());
        assertEquals(request.getCategory(), existingProduct.getCategory());
        assertEquals(request.getDescription(), existingProduct.getDescription());
    }

    @Test
    void updateProduct_ProductNotFound() {
        UpdateProductRequest request = UpdateProductRequest.builder()
                .id(1L)
                .name("Updated Golf Club Set")
                .price(new BigDecimal("599.99"))
                .category("Golf Equipment")
                .description("An updated complete set of golf clubs for all skill levels.")
                .build();

        when(productRepository.findById(request.getId())).thenReturn(Optional.empty());

        InvalidProductException exception = assertThrows(InvalidProductException.class, () -> {
            updateProductUseCase.updateProduct(request);
        });

        assertEquals("PRODUCT_ID_INVALID", exception.getMessage());
        verify(productRepository).findById(request.getId());
        verify(productRepository, never()).save(any(ProductEntity.class));
    }
}
