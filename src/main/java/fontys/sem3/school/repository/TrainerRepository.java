package fontys.sem3.school.repository;


import fontys.sem3.school.repository.entity.PlayerEntity;
import fontys.sem3.school.repository.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
}
