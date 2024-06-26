package fontys.sem3.school.repository;

import fontys.sem3.school.repository.entity.PlayerEntity;
import fontys.sem3.school.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%'))) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'asc' THEN p.price END ASC, " +
            "CASE WHEN :sort = 'desc' THEN p.price END DESC")
    List<ProductEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseWithSort(
    @Param("name") String name,
    @Param("category") String category,
    @Param("sort") String sort);

    @Query("SELECT p FROM ProductEntity p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<ProductEntity> findAllByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ur.role, COUNT(p) FROM ProductEntity p JOIN p.user u JOIN u.userRoles ur GROUP BY ur.role")
    List<Object[]> countProductsByRole();

}
