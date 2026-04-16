package br.com.school.product.api.product;

import br.com.school.product.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
