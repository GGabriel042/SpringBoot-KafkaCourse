package br.com.school.product.domain.product;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ProductEntity {
    private String id;
    private String sku;
    private String name;
    private BigDecimal stock;
    private BigDecimal cost;
    private BigDecimal price;

    private ProductEntity(String id,
                          String sku,
                          String name,
                          BigDecimal stock,
                          BigDecimal cost,
                          BigDecimal price) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.stock = stock;
        this.cost = cost;
        this.price = price;
    }

    public static ProductEntity create(String sku,
                                       String name,
                                       BigDecimal stock,
                                       BigDecimal cost,
                                       BigDecimal price) {
        final var id = UUID.randomUUID().toString();
        return new ProductEntity(id, sku, name, stock, cost, price);
    }

    private void selfValidate() {

    }
}
