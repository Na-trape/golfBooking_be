package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.GetProductsByMonthUseCase;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetProductsByMonthUseCaseImpl implements GetProductsByMonthUseCase {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsByMonth(int monthsAgo) {
        YearMonth currentMonth = YearMonth.now().minusMonths(monthsAgo);
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        List<ProductEntity> productEntities = productRepository.findAllByCreatedAtBetween(startDate, endDate);

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
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
