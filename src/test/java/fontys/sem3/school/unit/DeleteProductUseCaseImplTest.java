package fontys.sem3.school.unit;

import fontys.sem3.school.business.exception.InvalidProductException;
import fontys.sem3.school.business.impl.DeleteProductUseCaseImpl;
import fontys.sem3.school.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)

public class DeleteProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    @Test
    void deleteProduct_Success() {
        when(productRepository.existsById(anyLong())).thenReturn(true);

        deleteProductUseCase.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteProduct_Failure() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(InvalidProductException.class, () -> deleteProductUseCase.deleteProduct(1L));

        verify(productRepository, never()).deleteById(anyLong());
    }
}
