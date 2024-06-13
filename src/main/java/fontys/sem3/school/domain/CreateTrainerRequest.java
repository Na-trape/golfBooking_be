package fontys.sem3.school.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainerRequest {
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;

    @NotBlank
    @Length(min = 1, max = 20)
    private String hcp;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer experience;

    @NotBlank
    @Length(min = 10, max = 500)
    private String description;

    @NotBlank
    @Length(max = 50)
    private String password;

    @NotNull
    private Long countryId;
}
