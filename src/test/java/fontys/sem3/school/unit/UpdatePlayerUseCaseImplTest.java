package fontys.sem3.school.unit;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.exception.InvalidPlayerException;
import fontys.sem3.school.business.impl.UpdatePlayerUseCaseImpl;
import fontys.sem3.school.domain.UpdatePlayerRequest;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.CountryEntity;
import fontys.sem3.school.repository.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePlayerUseCaseImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CountryIdValidator countryIdValidator;

    @InjectMocks
    private UpdatePlayerUseCaseImpl updatePlayerUseCaseImpl;

    @Test
    void testUpdatePlayer_Success() {
        long playerId = 1L;
        long countryId = 2L;
        String playerName = "New Player Name";

        UpdatePlayerRequest request = UpdatePlayerRequest.builder()
                .id(playerId)
                .countryId(countryId)
                .name(playerName)
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(playerId)
                .name("Old Player Name")
                .country(CountryEntity.builder().id(1L).build())
                .build();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity));
        doNothing().when(countryIdValidator).validateId(countryId);
        when(playerRepository.save(any(PlayerEntity.class))).thenReturn(playerEntity);

        updatePlayerUseCaseImpl.updatePlayer(request);

        verify(playerRepository, times(1)).findById(playerId);
        verify(countryIdValidator, times(1)).validateId(countryId);
        verify(playerRepository, times(1)).save(any(PlayerEntity.class));

        assertEquals(countryId, playerEntity.getCountry().getId());
        assertEquals(playerName, playerEntity.getName());
    }

    @Test
    void testUpdatePlayer_InvalidPlayerId() {
        long playerId = 1L;

        UpdatePlayerRequest request = UpdatePlayerRequest.builder()
                .id(playerId)
                .countryId(2L)
                .name("New Player Name")
                .build();

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        assertThrows(InvalidPlayerException.class, () -> updatePlayerUseCaseImpl.updatePlayer(request));

        verify(playerRepository, times(1)).findById(playerId);
        verify(countryIdValidator, never()).validateId(anyLong());
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }

    @Test
    void testUpdatePlayer_InvalidCountryId() {
        long playerId = 1L;
        long invalidCountryId = 99L;

        UpdatePlayerRequest request = UpdatePlayerRequest.builder()
                .id(playerId)
                .countryId(invalidCountryId)
                .name("New Player Name")
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(playerId)
                .name("Old Player Name")
                .country(CountryEntity.builder().id(1L).build())
                .build();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity));
        doThrow(new RuntimeException("Invalid country ID")).when(countryIdValidator).validateId(invalidCountryId);

        assertThrows(RuntimeException.class, () -> updatePlayerUseCaseImpl.updatePlayer(request));

        verify(playerRepository, times(1)).findById(playerId);
        verify(countryIdValidator, times(1)).validateId(invalidCountryId);
        verify(playerRepository, never()).save(any(PlayerEntity.class));
    }
}