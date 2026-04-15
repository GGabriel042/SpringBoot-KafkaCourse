package br.com.school.product.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductEntity createProduct(ProductEntity entity) {
        return repository.save(entity);
    }

    public ProductEntity updateProduct(ProductEntity productFromBd, ProductEntity product) {
        productFromBd.update(product.getSku(), product.getName(), product.getStock(), product.getCost(), product.getPrice());
        return repository.save(productFromBd);
    }
}
