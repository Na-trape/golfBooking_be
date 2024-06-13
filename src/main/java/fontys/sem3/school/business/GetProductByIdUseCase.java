package fontys.sem3.school.business;

import fontys.sem3.school.domain.Product;

import java.util.Optional;

public interface GetProductByIdUseCase {
    Optional<Product> getProductById(Long id);
}
