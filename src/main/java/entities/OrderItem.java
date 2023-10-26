package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "en_order_item")
public class OrderItem extends BaseEntity {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_sale")
    private boolean productSale;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_id")
    private long orderId;
}
