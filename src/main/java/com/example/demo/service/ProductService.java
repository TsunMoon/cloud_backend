package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductOutput;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductOutput getAllProductDTOByPaging(int page, int limit, String keyword, String sortBy, int categoryId);

    int getLengthOfListSearch(String keyword);
}
