package entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "db_order")
public class Order extends BasEntity {

    @Column(name = "order_items")
    private List<OrderItem> orderItems;

    @Column(name = "total_price")
    private Double totalPrice;


}
