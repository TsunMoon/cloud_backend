package com.example.demo.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOutput {
    private int page;
    private int totalPage;
    private List<ProductDTO> listProductDTOS = new ArrayList<>();

}
