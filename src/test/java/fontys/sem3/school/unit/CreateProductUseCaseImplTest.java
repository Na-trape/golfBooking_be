//package fontys.sem3.school.unit;
//
//import fontys.sem3.school.business.impl.CreateProductUseCaseImpl;
//import fontys.sem3.school.configuration.security.token.AccessToken;
//import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
//import fontys.sem3.school.configuration.security.token.impl.AccessTokenImpl;
//import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
//import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;
//import fontys.sem3.school.repository.ProductRepository;
//import fontys.sem3.school.repository.UserRepository;
//import fontys.sem3.school.repository.entity.ProductEntity;
//import fontys.sem3.school.repository.entity.UserEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//
//public class CreateProductUseCaseImplTest {
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private AccessTokenDecoder accessTokenDecoder;
//
//    @InjectMocks
//    private CreateProductUseCaseImpl createProductUseCase;
//
//    private CreateProductRequest createProductRequest;
//    private AccessToken accessToken;
//    private UserEntity userEntity;
//
//    @BeforeEach
//    void setUp() {
//        createProductRequest = CreateProductRequest.builder()
//                .name("Test Product")
//                .price(BigDecimal.valueOf(19.99))
//                .category("Golf Equipment")
//                .description("A test product description")
//                .build();
//
//        Long placeholderExpiryTime = 0L;
//
//        accessToken = new AccessTokenImpl("TestUser", 1L, placeholderExpiryTime, List.of("USER"));
//
//        userEntity = UserEntity.builder()
//                .id(1L)
//                .username("TestUser")
//                .password("testPassword")
//                .build();
//    }
//
//    @Test
//    void createProduct_Success() {
//        String token = "validToken";
//
//        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
//        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
//
//        ProductEntity productEntity = ProductEntity.builder()
//                .id(1L)
//                .name("Test Product")
//                .price(BigDecimal.valueOf(19.99))
//                .category("Golf Equipment")
//                .description("A test product description")
//                .user(userEntity)
//                .build();
//
//        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
//
//        CreateProductResponse response = createProductUseCase.createProduct(createProductRequest, token);
//
//        assertNotNull(response);
//        assertEquals(1L, response.getProductId());
//
//        verify(accessTokenDecoder, times(1)).decode(token);
//        verify(userRepository, times(1)).findById(1L);
//        verify(productRepository, times(1)).save(any(ProductEntity.class));
//    }
//
//    @Test
//    void createProduct_InvalidUser() {
//        String token = "validToken";
//
//        when(accessTokenDecoder.decode(token)).thenReturn(accessToken);
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                createProductUseCase.createProduct(createProductRequest, token)
//        );
//
//        assertEquals("Invalid user ID", exception.getMessage());
//
//        verify(accessTokenDecoder, times(1)).decode(token);
//        verify(userRepository, times(1)).findById(1L);
//        verify(productRepository, times(0)).save(any(ProductEntity.class));
//    }
//}
