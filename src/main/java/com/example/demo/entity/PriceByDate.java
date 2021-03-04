package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "PriceByDate")
@Table(name = "price_by_date")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceByDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price"
//            , columnDefinition = "float"
            , nullable = false)
    private float price;

    @Column(name = "createAt", nullable = false)
    private Date createAt;

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
