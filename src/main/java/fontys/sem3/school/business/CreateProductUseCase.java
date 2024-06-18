package fontys.sem3.school.business;

import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;

public interface CreateProductUseCase {
    CreateProductResponse createProduct(CreateProductRequest request, String accessToken);
}
