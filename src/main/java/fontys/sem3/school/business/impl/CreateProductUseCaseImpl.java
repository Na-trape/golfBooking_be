package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CreateProductUseCase;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.ProductEntity;
import fontys.sem3.school.repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AccessTokenDecoder accessTokenDecoder;

    @Transactional
    @Override
    public CreateProductResponse createProduct(CreateProductRequest request, String accessToken) {
        AccessToken decodedToken = accessTokenDecoder.decode(accessToken);
        Long userId = decodedToken.getUserId();

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        UserEntity userEntity = userEntityOptional.get();

        ProductEntity productEntity = ProductEntity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .user(userEntity) // Set the user
                .build();

        ProductEntity savedProduct = productRepository.save(productEntity);

        return CreateProductResponse.builder()
                .productId(savedProduct.getId())
                .build();
    }
}
