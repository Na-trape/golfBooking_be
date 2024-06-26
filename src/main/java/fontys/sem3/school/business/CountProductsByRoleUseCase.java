package fontys.sem3.school.business;

import fontys.sem3.school.domain.ProductRequestResponse.RoleProductDTO;

import java.util.List;
import java.util.Map;

public interface CountProductsByRoleUseCase {
    List<RoleProductDTO> countProductsByRole();
}
