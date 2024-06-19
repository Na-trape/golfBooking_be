package fontys.sem3.school.unit;

import fontys.sem3.school.business.impl.GetProductsByMonthUseCaseImpl;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProductsByMonthUseCaseImplTest {
    private ProductRepository productRepository;
    private GetProductsByMonthUseCaseImpl getProductsByMonthUseCase;

    @BeforeEach
    public void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        getProductsByMonthUseCase = new GetProductsByMonthUseCaseImpl(productRepository);
    }

    @Test
    public void getProductsByMonth_WithProducts_ReturnsProducts() {
        YearMonth currentMonth = YearMonth.now().minusMonths(1);
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        List<ProductEntity> productEntities = Arrays.asList(new ProductEntity(), new ProductEntity());
        when(productRepository.findAllByCreatedAtBetween(startDate, endDate)).thenReturn(productEntities);

        assertEquals(2, getProductsByMonthUseCase.getProductsByMonth(1).size());
    }

    @Test
    public void getProductsByMonth_NoProducts_ReturnsEmptyList() {
        YearMonth currentMonth = YearMonth.now().minusMonths(1);
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        when(productRepository.findAllByCreatedAtBetween(startDate, endDate)).thenReturn(Arrays.asList());

        assertEquals(0, getProductsByMonthUseCase.getProductsByMonth(1).size());
    }
}
