package fontys.sem3.school.unit;

import fontys.sem3.school.business.GetPlayersUseCase;
import fontys.sem3.school.business.impl.GetPlayersUseCaseImpl;
import fontys.sem3.school.business.impl.PlayerConverter;
import fontys.sem3.school.domain.GetAllPlayersRequest;
import fontys.sem3.school.domain.GetAllPlayersResponse;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.CountryEntity;
import fontys.sem3.school.repository.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetPlayersTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GetPlayersUseCaseImpl getPlayersUseCaseImpl;

    @Test
    void testGetPlayers_WithCountryCode() {
        GetAllPlayersRequest request = GetAllPlayersRequest.builder()
                .countryCode("NL")
                .build();

        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .code("NL")
                .name("Netherlands")
                .build();

        PlayerEntity playerEntity1 = PlayerEntity.builder()
                .id(1L)
                .license(12345L)
                .name("Player 1")
                .country(country)
                .build();

        PlayerEntity playerEntity2 = PlayerEntity.builder()
                .id(2L)
                .license(67890L)
                .name("Player 2")
                .country(country)
                .build();

        List<PlayerEntity> playerEntities = Arrays.asList(playerEntity1, playerEntity2);

        when(playerRepository.findAllByCountry_CodeOrderByNameAsc(anyString())).thenReturn(playerEntities);

        GetAllPlayersResponse response = getPlayersUseCaseImpl.getPlayers(request);

        assertNotNull(response);
        assertEquals(2, response.getPlayers().size());

        Player expectedPlayer1 = PlayerConverter.convert(playerEntity1);
        Player expectedPlayer2 = PlayerConverter.convert(playerEntity2);

        Player actualPlayer1 = response.getPlayers().get(0);
        Player actualPlayer2 = response.getPlayers().get(1);

        assertEquals(expectedPlayer1.getId(), actualPlayer1.getId());
        assertEquals(expectedPlayer1.getLicense(), actualPlayer1.getLicense());
        assertEquals(expectedPlayer1.getName(), actualPlayer1.getName());
        assertEquals(expectedPlayer1.getCountry().getCode(), actualPlayer1.getCountry().getCode());
        assertEquals(expectedPlayer1.getCountry().getName(), actualPlayer1.getCountry().getName());

        assertEquals(expectedPlayer2.getId(), actualPlayer2.getId());
        assertEquals(expectedPlayer2.getLicense(), actualPlayer2.getLicense());
        assertEquals(expectedPlayer2.getName(), actualPlayer2.getName());
        assertEquals(expectedPlayer2.getCountry().getCode(), actualPlayer2.getCountry().getCode());
        assertEquals(expectedPlayer2.getCountry().getName(), actualPlayer2.getCountry().getName());

        verify(playerRepository, times(1)).findAllByCountry_CodeOrderByNameAsc(anyString());
    }

    @Test
    void testGetPlayers_WithoutCountryCode() {
        GetAllPlayersRequest request = new GetAllPlayersRequest();

        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .code("NL")
                .name("Netherlands")
                .build();

        PlayerEntity playerEntity1 = PlayerEntity.builder()
                .id(1L)
                .license(12345L)
                .name("Player 1")
                .country(country)
                .build();

        PlayerEntity playerEntity2 = PlayerEntity.builder()
                .id(2L)
                .license(67890L)
                .name("Player 2")
                .country(country)
                .build();

        List<PlayerEntity> playerEntities = Arrays.asList(playerEntity1, playerEntity2);

        when(playerRepository.findAll()).thenReturn(playerEntities);

        GetAllPlayersResponse response = getPlayersUseCaseImpl.getPlayers(request);

        assertNotNull(response);
        assertEquals(2, response.getPlayers().size());

        Player expectedPlayer1 = PlayerConverter.convert(playerEntity1);
        Player expectedPlayer2 = PlayerConverter.convert(playerEntity2);

        Player actualPlayer1 = response.getPlayers().get(0);
        Player actualPlayer2 = response.getPlayers().get(1);

        assertEquals(expectedPlayer1.getId(), actualPlayer1.getId());
        assertEquals(expectedPlayer1.getLicense(), actualPlayer1.getLicense());
        assertEquals(expectedPlayer1.getName(), actualPlayer1.getName());
        assertEquals(expectedPlayer1.getCountry().getCode(), actualPlayer1.getCountry().getCode());
        assertEquals(expectedPlayer1.getCountry().getName(), actualPlayer1.getCountry().getName());

        assertEquals(expectedPlayer2.getId(), actualPlayer2.getId());
        assertEquals(expectedPlayer2.getLicense(), actualPlayer2.getLicense());
        assertEquals(expectedPlayer2.getName(), actualPlayer2.getName());
        assertEquals(expectedPlayer2.getCountry().getCode(), actualPlayer2.getCountry().getCode());
        assertEquals(expectedPlayer2.getCountry().getName(), actualPlayer2.getCountry().getName());

        verify(playerRepository, times(1)).findAll();
    }
}
