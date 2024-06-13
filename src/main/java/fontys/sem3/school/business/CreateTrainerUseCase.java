package fontys.sem3.school.business;

import fontys.sem3.school.domain.CreateTrainerRequest;
import fontys.sem3.school.domain.CreateTrainerResponse;

public interface CreateTrainerUseCase {
    CreateTrainerResponse createTrainer(CreateTrainerRequest request);
}
