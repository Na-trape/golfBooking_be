package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.SearchPlayersUseCase;
import fontys.sem3.school.domain.Country;
import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPlayersUseCaseImpl implements SearchPlayersUseCase {
    private final PlayerRepository playerRepository;

    @Override
    public List<Player> searchPlayers(String name, Long license) {
        List<PlayerEntity> playerEntities;
        if (name != null && !name.isEmpty()) {
            playerEntities = playerRepository.findByNameContainingIgnoreCase(name);
        } else if (license != null) {
            playerEntities = playerRepository.findByLicenseContaining(license);
        } else {
            playerEntities = playerRepository.findAll();
        }
        return playerEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Player convertToDTO(PlayerEntity entity) {
        return Player.builder()
                .id(entity.getId())
                .license(entity.getLicense())
                .name(entity.getName())
                .country(entity.getCountry() != null ? new Country(entity.getCountry().getId(), entity.getCountry().getName(), entity.getCountry().getCode()) : null)
                .build();
    }
}
