package fontys.sem3.school.business;

import fontys.sem3.school.domain.GetAllPlayersRequest;
import fontys.sem3.school.domain.GetAllPlayersResponse;

public interface GetPlayersUseCase {
    GetAllPlayersResponse getPlayers(GetAllPlayersRequest request);
}
