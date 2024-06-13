package fontys.sem3.school.unit;
import fontys.sem3.school.business.impl.DeletePlayerUseCaseImpl;
import fontys.sem3.school.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePlayerUseCaseImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private DeletePlayerUseCaseImpl deletePlayerUseCaseImpl;

    @Test
    void testDeletePlayer() {
        long playerId = 1L;

        deletePlayerUseCaseImpl.deletePlayer(playerId);

        verify(playerRepository, times(1)).deleteById(playerId);
    }
}