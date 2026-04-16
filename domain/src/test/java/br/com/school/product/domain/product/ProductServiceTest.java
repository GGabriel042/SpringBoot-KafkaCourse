package br.com.school.product.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;


    @Test
    void shouldCreateNewProduct() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var product = ProductEntity.create(expectedSku, expectedName, expectedStock, expectedCost, expectedPrice);

        when(repository.save(any())).thenReturn(product);

        service.createProduct(product);

        Mockito.verify(repository, times(1))
                .save(argThat(arg -> Objects.equals(expectedSku, arg.getSku())
                        && Objects.equals(expectedName, arg.getName())
                        && Objects.equals(expectedStock, arg.getStock())
                        && Objects.equals(expectedCost, arg.getCost())
                        && Objects.equals(expectedPrice, arg.getPrice())));
    }


    @Test
    void shouldUpdateProduct() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var productFromBd = ProductEntity.create("123", "expectedName", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(30));

        final var product = ProductEntity.with(productFromBd.getId(),
                expectedSku,
                expectedName,
                expectedStock,
                expectedCost,
                expectedPrice);

        when(repository.save(any())).thenReturn(product);

        service.updateProduct(productFromBd, product);

        Mockito.verify(repository, times(1))
                .save(argThat(arg -> Objects.equals(expectedSku, arg.getSku())
                        && Objects.equals(expectedName, arg.getName())
                        && Objects.equals(expectedStock, arg.getStock())
                        && Objects.equals(expectedCost, arg.getCost())
                        && Objects.equals(expectedPrice, arg.getPrice())));
    }
}