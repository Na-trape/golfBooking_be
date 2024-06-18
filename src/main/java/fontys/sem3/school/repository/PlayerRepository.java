package fontys.sem3.school.repository;

import fontys.sem3.school.repository.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    PlayerEntity findByLicense(long license);

    boolean existsByLicense(long license);

    List<PlayerEntity> findAllByCountry_CodeOrderByNameAsc(String countryCode);
    List<PlayerEntity> findByNameContainingIgnoreCase(String name);

    List<PlayerEntity> findByLicenseContaining(Long license);
}
