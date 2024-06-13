package fontys.sem3.school.repository;

import fontys.sem3.school.repository.entity.PlayerEntity;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
