package fontys.sem3.school.business;

import fontys.sem3.school.domain.Player;

import java.util.Optional;

public interface GetPlayerUseCase {
    Optional<Player> getPlayer(long playerId);
}
