package br.com.school.product.domain.product;

import br.com.school.product.domain.exception.NotificationException;
import br.com.school.product.domain.validation.Error;
import br.com.school.product.domain.validation.NotificationValidation;
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
        final var notification = NotificationValidation.create();

        if (sku.isEmpty()) {
            notification.append(new Error("Sku cannot be null"));
        }

        final var nameLength = name.trim().length();
        if (nameLength < 5 || nameLength > 200) {
            notification.append(new Error("Name must contain between 5 and 200 characters"));
        }

        if (stock.compareTo(BigDecimal.ZERO) < 0) {
            notification.append(new Error("Stock must be positive"));
        }

        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            notification.append(new Error("Cost must be greater than 0"));
        }

        if (price.compareTo(cost) < 0) {
            notification.append(new Error("Price must be greater than cost"));
        }

        if (notification.hasErrors()) {
            throw new NotificationException("Failed to instance new product", notification);
        }
    }
}
