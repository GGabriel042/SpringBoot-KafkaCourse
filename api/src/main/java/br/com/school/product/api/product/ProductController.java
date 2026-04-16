package br.com.school.product.api.product;

import br.com.school.product.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {

        final var productEntity = request.toEntity();

        final var saveProduct = service.createProduct(productEntity);

        final var responseProduct = ProductResponse.fromEntity(saveProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseProduct);
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getALlProducts(Pageable pageable) {
        final var products = service.findAllProducts(pageable);
        final var responsePage = products.map(ProductResponse::fromEntity);
        return ResponseEntity.ok(responsePage);
    }
}
