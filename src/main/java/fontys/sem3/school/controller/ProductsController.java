package fontys.sem3.school.controller;

import fontys.sem3.school.business.*;
import fontys.sem3.school.domain.Product;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductRequest;
import fontys.sem3.school.domain.ProductRequestResponse.CreateProductResponse;
import fontys.sem3.school.domain.ProductRequestResponse.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final SearchProductsUseCase searchProductsUseCase;
    private final GetProductsByMonthUseCase getProductsByMonthUseCase;

    @PostMapping()
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request, HttpServletRequest httpRequest) {
        String accessToken = httpRequest.getHeader("Authorization").substring("Bearer ".length());
        CreateProductResponse response = createProductUseCase.createProduct(request, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest request) {
        request.setId(id);
        updateProductUseCase.updateProduct(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = getAllProductsUseCase.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = getProductByIdUseCase.getProductById(id);
        return productOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        List<Product> products = searchProductsUseCase.searchProducts(name, category, sort);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByMonth(
            @RequestParam(value = "monthsAgo", defaultValue = "0") int monthsAgo) {
        List<Product> products = getProductsByMonthUseCase.getProductsByMonth(monthsAgo);
        return ResponseEntity.ok(products);
    }
}
