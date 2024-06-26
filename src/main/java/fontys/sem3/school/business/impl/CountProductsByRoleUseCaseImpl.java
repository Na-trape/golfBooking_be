package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.CountProductsByRoleUseCase;
import fontys.sem3.school.domain.ProductRequestResponse.RoleProductDTO;
import fontys.sem3.school.repository.ProductRepository;
import fontys.sem3.school.repository.entity.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountProductsByRoleUseCaseImpl implements CountProductsByRoleUseCase {
    private final ProductRepository productRepository;

    @Override
    public List<RoleProductDTO> countProductsByRole() {
        List<Object[]> result = productRepository.countProductsByRole();
        return result.stream()
                .map(obj -> new RoleProductDTO(((RoleEnum) obj[0]).name(), (Long) obj[1]))
                .collect(Collectors.toList());
    }
}
