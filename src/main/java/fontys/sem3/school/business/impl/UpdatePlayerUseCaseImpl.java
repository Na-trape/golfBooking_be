package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.UpdatePlayerUseCase;
import fontys.sem3.school.business.exception.InvalidPlayerException;
import fontys.sem3.school.domain.UpdatePlayerRequest;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.CountryEntity;
import fontys.sem3.school.repository.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdatePlayerUseCaseImpl implements UpdatePlayerUseCase {
    private final PlayerRepository playerRepository;
    private final CountryIdValidator countryIdValidator;

    @Transactional
    @Override
    public void updatePlayer(UpdatePlayerRequest request) {
        Optional<PlayerEntity> playerOptional = playerRepository.findById(request.getId());
        if (playerOptional.isEmpty()) {
            throw new InvalidPlayerException("PLAYER_ID_INVALID");
        }

        countryIdValidator.validateId(request.getCountryId());

        PlayerEntity player = playerOptional.get();
        updateFields(request, player);
    }

    private void updateFields(UpdatePlayerRequest request, PlayerEntity player) {
        player.setCountry(CountryEntity.builder().id(request.getCountryId()).build());
        player.setName(request.getName());

        playerRepository.save(player);
    }
}
