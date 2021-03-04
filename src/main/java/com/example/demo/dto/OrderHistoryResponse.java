package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderHistoryResponse {
    private int orderId;
    private Date createAt;
    private String phoneNumber;
    private List<OrderHistoryDetail> listOrderHistoryDetails;

}
