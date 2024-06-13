package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private Long id;
    private String name;
    private String hcp;
    private Integer experience;
    private String description;
    private Country country;
}
