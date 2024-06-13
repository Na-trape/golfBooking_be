package fontys.sem3.school.unit;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.impl.CreatePlayerUseCaseImpl;
import fontys.sem3.school.domain.CreatePlayerRequest;
import fontys.sem3.school.domain.CreatePlayerResponse;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatePlayerUseCaseImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CountryIdValidator countryIdValidator;

    @InjectMocks
    private CreatePlayerUseCaseImpl createPlayerUseCase;

    @Test
    void createPlayer_Success() {
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("Player 1")
                .countryId(1L)
                .license(12222L)
                .password("password")
                .build();

        when(playerRepository.existsByLicense(12222L)).thenReturn(false);
        when(playerRepository.save(any(PlayerEntity.class))).thenAnswer(invocation -> {
            PlayerEntity playerEntity = invocation.getArgument(0);
            playerEntity.setId(1L);
            return playerEntity;
        });

        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        assertNotNull(response);
        assertEquals(1L, response.getPlayerId());
    }

}
