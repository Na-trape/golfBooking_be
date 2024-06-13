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
public class CreatePlayerRequest {
    @NotNull
    @Min(1)
    @Max(100000)
    private Long license;

    @NotBlank
    private String name;

    @NotBlank
    @Length(max = 50)
    private String password;

    @NotNull
    private Long countryId;
}
