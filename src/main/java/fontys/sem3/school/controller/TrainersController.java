package fontys.sem3.school.controller;


import fontys.sem3.school.business.CreateTrainerUseCase;
import fontys.sem3.school.domain.CreateTrainerRequest;
import fontys.sem3.school.domain.CreateTrainerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainersController {
    private final CreateTrainerUseCase createTrainerUseCase;

    @PostMapping()
    public ResponseEntity<CreateTrainerResponse> createTrainer(@RequestBody @Valid CreateTrainerRequest request) {
        CreateTrainerResponse response = createTrainerUseCase.createTrainer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
