package fontys.sem3.school.unit;

import fontys.sem3.school.business.CountryIdValidator;
import fontys.sem3.school.business.impl.CreateTrainerUseCaseImpl;
import fontys.sem3.school.domain.CreateTrainerRequest;
import fontys.sem3.school.domain.CreateTrainerResponse;
import fontys.sem3.school.repository.TrainerRepository;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.TrainerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTrainerUseCaseImplTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CountryIdValidator countryIdValidator;

    @InjectMocks
    private CreateTrainerUseCaseImpl createTrainerUseCase;

    @Test
    void createTrainer_Success() {
        CreateTrainerRequest request = CreateTrainerRequest.builder()
                .name("Trainer 1")
                .countryId(1L)
                .hcp("10")
                .experience(5)
                .description("Description 1")
                .password("password")
                .build();

        when(trainerRepository.save(any(TrainerEntity.class))).thenAnswer(invocation -> {
            TrainerEntity trainerEntity = invocation.getArgument(0);
            trainerEntity.setId(1L);
            return trainerEntity;
        });

        doNothing().when(countryIdValidator).validateId(any(Long.class));

        CreateTrainerResponse response = createTrainerUseCase.createTrainer(request);

        assertNotNull(response);
        assertEquals(1L, response.getTrainerId());

        verify(countryIdValidator).validateId(request.getCountryId());
    }
}
