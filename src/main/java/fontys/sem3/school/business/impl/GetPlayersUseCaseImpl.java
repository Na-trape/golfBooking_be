package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.GetPlayersUseCase;
import fontys.sem3.school.domain.GetAllPlayersRequest;
import fontys.sem3.school.domain.GetAllPlayersResponse;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPlayersUseCaseImpl implements GetPlayersUseCase {
    private PlayerRepository playerRepository;

    @Override
    public GetAllPlayersResponse getPlayers(final GetAllPlayersRequest request) {
        List<PlayerEntity> results;
        if (StringUtils.hasText(request.getCountryCode())) {
            results = playerRepository.findAllByCountry_CodeOrderByNameAsc(request.getCountryCode());
        } else {
            results = playerRepository.findAll();
        }

        final GetAllPlayersResponse response = new GetAllPlayersResponse();
        List<Player> playersDTO = results
                .stream()
                .map(PlayerConverter::convert)
                .toList();
        response.setPlayers(playersDTO);

        return response;
    }
}
