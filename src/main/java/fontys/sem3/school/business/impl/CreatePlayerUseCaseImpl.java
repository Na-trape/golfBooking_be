package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.CreatePlayerUseCase;
import fontys.sem3.school.business.exception.LicenseAlreadyExistsException;
import fontys.sem3.school.domain.CreatePlayerRequest;
import fontys.sem3.school.domain.CreatePlayerResponse;
import fontys.sem3.school.repository.PlayerRepository;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {


    private final PlayerRepository playerRepository;
    private final CountryIdValidator countryIdValidator;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreatePlayerResponse createPlayer(CreatePlayerRequest request) {
        if (playerRepository.existsByLicense(request.getLicense())) {
            throw new LicenseAlreadyExistsException();
        }

        countryIdValidator.validateId(request.getCountryId());

        PlayerEntity savedPlayer = saveNewPlayer(request);

        saveNewUser(savedPlayer, request.getPassword());

        return CreatePlayerResponse.builder()
                .playerId(savedPlayer.getId())
                .build();
    }

    private void saveNewUser(PlayerEntity player, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .username(player.getName().toString())
                .password(encodedPassword)
                .player(player)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.PLAYER)
                        .build()));
        userRepository.save(newUser);
    }

    private PlayerEntity saveNewPlayer(CreatePlayerRequest request) {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .country(CountryEntity.builder().id(request.getCountryId()).build())
                .name(request.getName())
                .license(request.getLicense())
                .build();
        return playerRepository.save(newPlayer);
    }
}
