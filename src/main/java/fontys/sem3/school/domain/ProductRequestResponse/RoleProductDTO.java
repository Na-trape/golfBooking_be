package fontys.sem3.school.domain.ProductRequestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleProductDTO {
    private String role;
    private Long count;
}
