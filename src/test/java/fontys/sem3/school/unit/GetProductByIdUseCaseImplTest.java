package fontys.sem3.school.unit;

import fontys.sem3.school.business.exception.ProductNotFoundException;
import fontys.sem3.school.business.impl.GetProductByIdUseCaseImpl;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductByIdUseCaseImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdUseCaseImpl getProductByIdUseCase;

    @Test
    void getProductById_Success() {
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name("Golf Club Set")
                .price(new BigDecimal("499.99"))
                .category("Golf Equipment")
                .description("A complete set of golf clubs for all skill levels.")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        Optional<Product> productOptional = getProductByIdUseCase.getProductById(1L);

        assertTrue(productOptional.isPresent());
        Product product = productOptional.get();
        assertEquals(productEntity.getId(), product.getId());
        assertEquals(productEntity.getName(), product.getName());
        assertEquals(productEntity.getPrice(), product.getPrice());
        assertEquals(productEntity.getCategory(), product.getCategory());
        assertEquals(productEntity.getDescription(), product.getDescription());

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            getProductByIdUseCase.getProductById(1L);
        });

        verify(productRepository).findById(1L);
    }
}
