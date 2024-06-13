package fontys.sem3.school.domain.ProductRequestResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductResponse {
    private Long productId;
}
