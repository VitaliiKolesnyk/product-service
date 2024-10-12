package org.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItem {

    private Product product;

    private int quantity;

    private Double totalPrice;

    public void calculateTotalPrice() {
        this.totalPrice = this.quantity * product.getPrice();
    }
}
