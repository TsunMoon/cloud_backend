package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderHistoryDetail {
    private String name;
    private int amount;
    private float priceDetail;
    private String category;
    private String image;
}
