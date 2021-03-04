package com.example.demo.service;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.OrderHistoryResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ThreeInformationDTO;
import com.example.demo.entity.Product;

import java.util.List;

public interface AdminService {
    ProductDTO getProductById(int id);
    ProductDTO createNewProduct(ProductDTO productDTO);
    ProductDTO updateProduct(int id, ProductDTO productDTO);
    String deleteProduct(int id);
    String updateIsUsedProduct(int id);


    List<OrderHistoryResponse> getAllOrderHistory();
    List<OrderHistoryResponse> searchOrderHistory(String phoneNumber);
    ThreeInformationDTO getThreeInformation();
}
