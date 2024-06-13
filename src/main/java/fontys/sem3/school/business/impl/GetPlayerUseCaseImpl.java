package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.GetPlayerUseCase;
import fontys.sem3.school.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPlayerUseCaseImpl implements GetPlayerUseCase {

    private PlayerRepository playerRepository;
    private AccessToken requestAccessToken;

    @Override
    public Optional<Player> getPlayer(long playerId) {
        if (!requestAccessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (requestAccessToken.getPlayerId() != playerId) {
                throw new UnauthorizedDataAccessException("PLAYER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        return playerRepository.findById(playerId).map(PlayerConverter::convert);
    }
}
