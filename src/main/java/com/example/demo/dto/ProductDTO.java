package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    public int id;
    public String name;
    public int quantity;
    public float price;
    public String category;
    public String image;
    public boolean isUsed;


}
