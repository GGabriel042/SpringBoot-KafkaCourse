package br.com.school.product.domain.product;

import br.com.school.product.domain.exception.NotificationException;
import br.com.school.product.domain.validation.Error;
import br.com.school.product.domain.validation.NotificationValidation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor
public class ProductEntity {

    @Id
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
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.sku = Objects.requireNonNull(sku, "Sku cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.stock = Objects.requireNonNull(stock, "Stock cannot be null");
        this.cost = Objects.requireNonNull(cost, "Cost cannot be null");
        this.price = Objects.requireNonNull(price, "Price cannot be null");
    }

    public static ProductEntity create(String sku,
                                       String name,
                                       BigDecimal stock,
                                       BigDecimal cost,
                                       BigDecimal price) {
        final var id = UUID.randomUUID().toString();
        final var product = new ProductEntity(id, sku, name, stock, cost, price);
        product.selfValidate();
        return product;
    }

    public static ProductEntity with(ProductEntity productSource) {
        return with(
                productSource.getId(),
                productSource.getSku(),
                productSource.getName(),
                productSource.getStock(),
                productSource.getCost(),
                productSource.getPrice());
    }

    public static ProductEntity with(String id,
                                     String sku,
                                     String name,
                                     BigDecimal stock,
                                     BigDecimal cost,
                                     BigDecimal price) {
        return new ProductEntity(id,
                sku,
                name,
                stock,
                cost,
                price);
    }

    public void update(String sku,
                       String name,
                       BigDecimal stock,
                       BigDecimal cost,
                       BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.stock = stock;
        this.cost = cost;
        this.price = price;
        selfValidate();
    }


    private void selfValidate() {
        final var notification = NotificationValidation.create();

        if (sku == null || sku.isEmpty()) {
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

        if (price.compareTo(cost) <= 0) {
            notification.append(new Error("Price must be greater than cost"));
        }

        if (notification.hasErrors()) {
            throw new NotificationException("Failed to instance new product", notification);
        }
    }
}
