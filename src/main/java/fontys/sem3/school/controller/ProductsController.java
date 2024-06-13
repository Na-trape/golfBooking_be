package fontys.sem3.school.controller;

import fontys.sem3.school.business.CreateProductUseCase;
import fontys.sem3.school.business.DeleteProductUseCase;
import fontys.sem3.school.business.GetAllProductsUseCase;
import fontys.sem3.school.business.UpdateProductUseCase;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;

    @PostMapping()
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        CreateProductResponse response = createProductUseCase.createProduct(request);
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
}
