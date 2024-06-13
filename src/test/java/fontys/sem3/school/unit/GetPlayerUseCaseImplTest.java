package fontys.sem3.school.unit;

import fontys.sem3.school.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.school.business.impl.GetPlayerUseCaseImpl;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import fontys.sem3.school.repository.entity.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPlayerUseCaseImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    private GetPlayerUseCaseImpl getPlayerUseCaseImpl;


//    @Test
//    void testGetPlayer_AsPlayer() {
//        long playerId = 1L;
//
//        PlayerEntity playerEntity = PlayerEntity.builder()
//                .id(playerId)
//                .license(12345L)
//                .name("Player 1")
//                .build();
//
//        when(requestAccessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
//        when(requestAccessToken.getPlayerId()).thenReturn(playerId);
//        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity));
//
//        Optional<Player> result = getPlayerUseCaseImpl.getPlayer(playerId);
//
//        assertTrue(result.isPresent());
//        assertEquals(playerId, result.get().getId());
//        assertEquals("Player 1", result.get().getName());
//    }

    @Test
    void testGetPlayer_Unauthorized() {
        long playerId = 1L;
        long differentPlayerId = 2L;

        when(requestAccessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(requestAccessToken.getPlayerId()).thenReturn(differentPlayerId);

        assertThrows(UnauthorizedDataAccessException.class, () -> getPlayerUseCaseImpl.getPlayer(playerId));
    }
}