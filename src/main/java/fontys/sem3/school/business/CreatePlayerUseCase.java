package fontys.sem3.school.business;

import fontys.sem3.school.domain.CreatePlayerRequest;
import fontys.sem3.school.domain.CreatePlayerResponse;

public interface CreatePlayerUseCase {
    CreatePlayerResponse createPlayer(CreatePlayerRequest request);
}
