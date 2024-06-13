package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.CreatePlayerUseCase;
import fontys.sem3.school.business.CreateTrainerUseCase;
import fontys.sem3.school.domain.CreateTrainerRequest;
import fontys.sem3.school.domain.CreateTrainerResponse;
import fontys.sem3.school.repository.TrainerRepository;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateTrainerUseCaseImpl implements CreateTrainerUseCase {
    private final TrainerRepository trainerRepository;
    private final CountryIdValidator countryIdValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateTrainerResponse createTrainer(CreateTrainerRequest request) {
        countryIdValidator.validateId(request.getCountryId());

        TrainerEntity savedTrainer = saveNewTrainer(request);

        saveNewUser(savedTrainer, request.getPassword());

        return CreateTrainerResponse.builder()
                .trainerId(savedTrainer.getId())
                .build();
    }

    private void saveNewUser(TrainerEntity trainer, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .username(trainer.getName())
                .password(encodedPassword)
                .trainer(trainer)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.TRAINER)
                        .build()));
        userRepository.save(newUser);
    }

    private TrainerEntity saveNewTrainer(CreateTrainerRequest request) {
        TrainerEntity newTrainer = TrainerEntity.builder()
                .country(CountryEntity.builder().id(request.getCountryId()).build())
                .name(request.getName())
                .hcp(request.getHcp())
                .experience(request.getExperience())
                .description(request.getDescription())
                .build();
        return trainerRepository.save(newTrainer);
    }
}
