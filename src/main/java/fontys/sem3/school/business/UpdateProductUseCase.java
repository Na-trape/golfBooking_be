package fontys.sem3.school.business;

import fontys.sem3.school.domain.ProductRequestResponse.UpdateProductRequest;

public interface UpdateProductUseCase {
    void updateProduct(UpdateProductRequest request);
}
