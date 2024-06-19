package fontys.sem3.school.unit;

import fontys.sem3.school.business.impl.SearchPlayersUseCaseImpl;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SearchPlayersUseCaseImplTest {
    private PlayerRepository playerRepository;
    private SearchPlayersUseCaseImpl searchPlayersUseCase;

    @BeforeEach
    public void setup() {
        playerRepository = Mockito.mock(PlayerRepository.class);
        searchPlayersUseCase = new SearchPlayersUseCaseImpl(playerRepository);
    }

    @Test
    public void searchPlayers_ByName_ReturnsPlayers() {
        List<PlayerEntity> playerEntities = Arrays.asList(new PlayerEntity(), new PlayerEntity());
        when(playerRepository.findByNameContainingIgnoreCaseAndLicense("name", null)).thenReturn(playerEntities);

        List<Player> players = searchPlayersUseCase.searchPlayers("name", null);
        assertEquals(2, players.size());
    }

    @Test
    public void searchPlayers_ByLicense_ReturnsPlayers() {
        List<PlayerEntity> playerEntities = Arrays.asList(new PlayerEntity(), new PlayerEntity());
        when(playerRepository.findByNameContainingIgnoreCaseAndLicense(null, 123L)).thenReturn(playerEntities);

        List<Player> players = searchPlayersUseCase.searchPlayers(null, 123L);
        assertEquals(2, players.size());
    }

    @Test
    public void searchPlayers_NoSearchParameters_ReturnsAllPlayers() {
        List<PlayerEntity> playerEntities = Arrays.asList(new PlayerEntity(), new PlayerEntity());
        when(playerRepository.findByNameContainingIgnoreCaseAndLicense(null, null)).thenReturn(playerEntities);

        List<Player> players = searchPlayersUseCase.searchPlayers(null, null);
        assertEquals(2, players.size());
    }
}
