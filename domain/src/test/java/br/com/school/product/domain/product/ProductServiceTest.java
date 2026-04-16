package br.com.school.product.domain.product;

import br.com.school.product.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

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


    @Test
    void shouldReturnProductGetById() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var product = ProductEntity.create(expectedSku, expectedName, expectedStock, expectedCost, expectedPrice);

        when(repository.findById(any())).thenReturn(Optional.of(product));

        final var actualProduct = service.getProductById("1");

        Assertions.assertTrue(
                Objects.equals(expectedSku, actualProduct.getSku())
                        && Objects.equals(expectedName, actualProduct.getName())
                        && Objects.equals(expectedStock, actualProduct.getStock())
                        && Objects.equals(expectedCost, actualProduct.getCost())
                        && Objects.equals(expectedPrice, actualProduct.getPrice())
        );

        Mockito.verify(repository, times(1))
                .findById("1");
    }

    @Test
    void shouldNotReturnProductGetByIdWhenProductNotFound() {

        final var expectedMessageError = "Product with id 1 not found";

        when(repository.findById(any())).thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(NotFoundException.class,
                () -> service.getProductById("1"));

        Assertions.assertEquals(expectedMessageError, exception.getMessage());

        Mockito.verify(repository, times(1))
                .findById("1");
    }
}