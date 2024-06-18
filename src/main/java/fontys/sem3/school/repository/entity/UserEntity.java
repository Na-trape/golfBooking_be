package fontys.sem3.school.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 20)
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @Length(max = 100)
    private String password;

    @OneToOne(optional = true)
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @OneToOne(optional = true)
    @JoinColumn(name = "trainer_id")
    private TrainerEntity trainer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<UserRoleEntity> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductEntity> products;
}
