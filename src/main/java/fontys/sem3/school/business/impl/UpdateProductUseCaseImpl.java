package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.UpdateProductUseCase;
import fontys.sem3.school.business.exception.InvalidProductException;
import fontys.sem3.school.domain.ProductRequestResponse.UpdateProductRequest;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpdateProductUseCaseImpl.class);

    @Transactional
    @Override
    public void updateProduct(UpdateProductRequest request) {
        logger.debug("Received request to update product: {}", request);

        Optional<ProductEntity> productOptional = productRepository.findById(request.getId());
        if (productOptional.isEmpty()) {
            logger.error("Product with ID {} not found", request.getId());
            throw new InvalidProductException("PRODUCT_ID_INVALID");
        }

        ProductEntity product = productOptional.get();
        updateFields(request, product);
        logger.debug("Updated product: {}", product);
    }

    private void updateFields(UpdateProductRequest request, ProductEntity product) {
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());

        productRepository.save(product);
        logger.debug("Product saved: {}", product);
    }
}
