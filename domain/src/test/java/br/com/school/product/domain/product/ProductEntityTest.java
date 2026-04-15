package br.com.school.product.domain.product;

import br.com.school.product.domain.exception.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class ProductEntityTest {

    @Test
    void shouldInstanceNewProduct() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var product = ProductEntity.create(expectedSku, expectedName, expectedStock, expectedCost, expectedPrice);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(expectedSku, product.getSku());
        Assertions.assertEquals(expectedName, product.getName());
        Assertions.assertEquals(expectedStock, product.getStock());
        Assertions.assertEquals(expectedCost, product.getCost());
        Assertions.assertEquals(expectedPrice, product.getPrice());
    }


    private static Stream<Arguments> getPossiblesOfValueProduct() {

        final var bigText = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901";

        return Stream.of(
                Arguments.of("", "product name", BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(20), "Sku cannot be null"),
                Arguments.of("1", "prod    ", BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(20), "Name must contain between 5 and 200 characters"),
                Arguments.of("1", bigText, BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(20), "Name must contain between 5 and 200 characters"),
                Arguments.of("1", "product name", BigDecimal.valueOf(-1), BigDecimal.valueOf(10), BigDecimal.valueOf(20), "Stock must be positive"),
                Arguments.of("1", "product name", BigDecimal.valueOf(1), BigDecimal.valueOf(0), BigDecimal.valueOf(20), "Cost must be greater than 0"),
                Arguments.of("1", "product name", BigDecimal.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(10), "Price must be greater than cost")
        );
    }


    @ParameterizedTest
    @MethodSource("getPossiblesOfValueProduct")
    void shouldNotInstanceNewProduct(String sku,
                                     String name,
                                     BigDecimal stock,
                                     BigDecimal cost,
                                     BigDecimal price,
                                     String message) {

        final var expectedError = Assertions.assertThrows(NotificationException.class, () -> ProductEntity.create(sku, name, stock, cost, price));

        Assertions.assertEquals(message, expectedError.getErrors().get(0).message());
    }


    @Test
    void shouldInstanceNewProductAndUpdate() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var product = ProductEntity.create("123", "name name name", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(200));


        final var id = product.getId();

        product.update(expectedSku, expectedName, expectedStock, expectedCost, expectedPrice);


        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(id, product.getId());
        Assertions.assertEquals(expectedSku, product.getSku());
        Assertions.assertEquals(expectedName, product.getName());
        Assertions.assertEquals(expectedStock, product.getStock());
        Assertions.assertEquals(expectedCost, product.getCost());
        Assertions.assertEquals(expectedPrice, product.getPrice());
    }


    @ParameterizedTest
    @MethodSource("getPossiblesOfValueProduct")
    void shouldInstanceNewProductAndNotUpdate(String sku,
                                              String name,
                                              BigDecimal stock,
                                              BigDecimal cost,
                                              BigDecimal price,
                                              String message) {


        final var product = ProductEntity.create("123", "name name name", BigDecimal.valueOf(100), BigDecimal.valueOf(100), BigDecimal.valueOf(200));


        final var expectedError = Assertions.assertThrows(NotificationException.class, () -> product.update(sku, name, stock, cost, price));

        Assertions.assertEquals(message, expectedError.getErrors().get(0).message());
    }

}