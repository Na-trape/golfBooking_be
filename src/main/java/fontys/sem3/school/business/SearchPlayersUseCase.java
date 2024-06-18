package fontys.sem3.school.business;

import fontys.sem3.school.domain.Player;

import java.util.List;

public interface SearchPlayersUseCase {
    List<Player> searchPlayers(String name, Long license);
}
