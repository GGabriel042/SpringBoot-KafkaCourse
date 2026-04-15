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


    @Test
    void shouldInstanceNewProductWithAllErrors() {
        final var expectedSku = "";
        final var expectedName = "Prod";
        final var expectedStock = BigDecimal.valueOf(-1);
        final var expectedCost = BigDecimal.valueOf(0);
        final var expectedPrice = BigDecimal.valueOf(0);
        final var expectedTotalErros = 5;


        final var expectedError = Assertions.assertThrows(NotificationException.class, () -> ProductEntity.create(expectedSku, expectedName, expectedStock, expectedCost, expectedPrice));

        Assertions.assertNotNull(expectedError);
        Assertions.assertEquals(expectedTotalErros, expectedError.getErrors().size());
    }


    @Test
    void shouldNotInstanceNewProductWhenValuesAreNull() {
        final var expectedSku = "1";
        final var expectedName = "Product name";
        final var expectedStock = BigDecimal.valueOf(10);
        final var expectedCost = BigDecimal.valueOf(20);
        final var expectedPrice = BigDecimal.valueOf(30);


        final var expectedErrorSku = Assertions.assertThrows(NotificationException.class,
                () -> ProductEntity.create(null, expectedName, expectedStock, expectedCost, expectedPrice));
        Assertions.assertEquals("Sku cannot be null", expectedErrorSku.getMessage());

        final var expectedErrorName = Assertions.assertThrows(NotificationException.class,
                () -> ProductEntity.create(expectedSku, null, expectedStock, expectedCost, expectedPrice));
        Assertions.assertEquals("Name cannot be null", expectedErrorName.getMessage());

        final var expectedErrorStock = Assertions.assertThrows(NotificationException.class,
                () -> ProductEntity.create(expectedSku, expectedName, null, expectedCost, expectedPrice));
        Assertions.assertEquals("Stock cannot be null", expectedErrorStock.getMessage());

        final var expectedErrorCost = Assertions.assertThrows(NotificationException.class,
                () -> ProductEntity.create(expectedSku, expectedName, expectedStock, null, expectedPrice));
        Assertions.assertEquals("Cost cannot be null", expectedErrorCost.getMessage());

        final var expectedErrorPrice = Assertions.assertThrows(NotificationException.class,
                () -> ProductEntity.create(expectedSku, expectedName, expectedStock, expectedCost, null));
        Assertions.assertEquals("Price cannot be null", expectedErrorPrice.getMessage());
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