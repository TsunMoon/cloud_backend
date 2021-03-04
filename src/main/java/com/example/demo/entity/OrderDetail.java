package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "OrderDetail")
@Table(name = "order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "price_detail"
//            ,columnDefinition = "float"
            ,nullable = false)
    private float priceDetail;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
