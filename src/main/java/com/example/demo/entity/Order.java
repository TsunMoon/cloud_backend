package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name = "Order")
@Table(name = "[order]")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createAt", nullable = false)
    private Date createAt;

    @Column(name = "phoneNumber")
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> listOrderDetails;


}
