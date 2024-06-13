package fontys.sem3.school.repository.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "trainer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Length(min = 1, max = 20)
    @Column(name = "hcp")
    private String hcp;

    @NotNull
    @Column(name = "experience")
    private Integer experience;

    @NotBlank
    @Length(min = 10, max = 500)
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;
}
