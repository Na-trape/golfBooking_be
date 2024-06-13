package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.DeleteProductUseCase;
import fontys.sem3.school.business.exception.InvalidProductException;
import fontys.sem3.school.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new InvalidProductException("PRODUCT_ID_INVALID");
        }
        productRepository.deleteById(productId);
    }
}
