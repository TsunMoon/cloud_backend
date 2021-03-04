package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Product")
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name"
//            , columnDefinition = "nvarchar(50)"
            , nullable = false)
    private String name;

    @Column(name = "image"
//            , columnDefinition = "nvarchar(MAX)"
    )
    private String image;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "is_used")
    private boolean isUsed;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PriceByDate> listPriceByDates;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> listOrderDetails;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
