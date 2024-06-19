package fontys.sem3.school.repository;

import fontys.sem3.school.repository.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    PlayerEntity findByLicense(long license);

    boolean existsByLicense(long license);

    List<PlayerEntity> findAllByCountry_CodeOrderByNameAsc(String countryCode);
    List<PlayerEntity> findByNameContainingIgnoreCase(String name);

    List<PlayerEntity> findByLicenseContaining(Long license);

    @Query("SELECT p FROM PlayerEntity p WHERE (:name is null or lower(p.name) like lower(concat('%', :name,'%'))) and (:license is null or cast(p.license as string) like concat('%', cast(:license as string), '%'))")
    List<PlayerEntity> findByNameContainingIgnoreCaseAndLicense(@Param("name") String name, @Param("license") Long license);}
