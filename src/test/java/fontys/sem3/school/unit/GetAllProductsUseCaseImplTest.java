package fontys.sem3.school.unit;

import fontys.sem3.school.business.impl.GetAllProductsUseCaseImpl;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllProductsUseCaseImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetAllProductsUseCaseImpl getAllProductsUseCase;

    @Test
    void getAllProducts_Success() {
        ProductEntity product1 = ProductEntity.builder()
                .id(1L)
                .name("Golf Club Set")
                .price(new BigDecimal("499.99"))
                .category("Golf Equipment")
                .description("A complete set of golf clubs for all skill levels.")
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .id(2L)
                .name("Golf Ball Pack")
                .price(new BigDecimal("29.99"))
                .category("Golf Accessories")
                .description("A pack of 12 golf balls.")
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = getAllProductsUseCase.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());

        assertEquals(product1.getId(), products.get(0).getId());
        assertEquals(product1.getName(), products.get(0).getName());
        assertEquals(product1.getPrice(), products.get(0).getPrice());
        assertEquals(product1.getCategory(), products.get(0).getCategory());
        assertEquals(product1.getDescription(), products.get(0).getDescription());

        assertEquals(product2.getId(), products.get(1).getId());
        assertEquals(product2.getName(), products.get(1).getName());
        assertEquals(product2.getPrice(), products.get(1).getPrice());
        assertEquals(product2.getCategory(), products.get(1).getCategory());
        assertEquals(product2.getDescription(), products.get(1).getDescription());
    }
}
