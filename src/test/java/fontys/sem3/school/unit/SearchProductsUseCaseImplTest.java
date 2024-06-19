package fontys.sem3.school.unit;

import fontys.sem3.school.business.impl.SearchProductsUseCaseImpl;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SearchProductsUseCaseImplTest {
    private ProductRepository productRepository;
    private SearchProductsUseCaseImpl searchProductsUseCase;

    @BeforeEach
    public void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        searchProductsUseCase = new SearchProductsUseCaseImpl(productRepository);
    }

    @Test
    public void searchProducts_WithProducts_ReturnsProducts() {
        List<ProductEntity> productEntities = Arrays.asList(new ProductEntity(), new ProductEntity());
        when(productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseWithSort("name", "category", "sort")).thenReturn(productEntities);

        List<Product> products = searchProductsUseCase.searchProducts("name", "category", "sort");
        assertEquals(2, products.size());
    }

    @Test
    public void searchProducts_NoProducts_ReturnsEmptyList() {
        when(productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseWithSort("name", "category", "sort")).thenReturn(Arrays.asList());

        List<Product> products = searchProductsUseCase.searchProducts("name", "category", "sort");
        assertEquals(0, products.size());
    }
}
