package fontys.sem3.school.business;

import fontys.sem3.school.domain.Product;

import java.util.List;

public interface SearchProductsUseCase {
    List<Product> searchProducts(String name, String category, String sort);
}
