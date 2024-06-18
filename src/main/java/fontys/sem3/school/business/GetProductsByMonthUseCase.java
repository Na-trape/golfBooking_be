package fontys.sem3.school.business;

import fontys.sem3.school.domain.Product;

import java.util.List;

public interface GetProductsByMonthUseCase {
    List<Product> getProductsByMonth(int monthsAgo);
}
