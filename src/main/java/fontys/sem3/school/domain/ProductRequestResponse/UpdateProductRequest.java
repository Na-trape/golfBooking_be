package fontys.sem3.school.domain.ProductRequestResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    private BigDecimal price;

    @NotBlank
    @Size(min = 2, max = 50)
    private String category;

    @Size(max = 500)
    private String description;
}
