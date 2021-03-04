package com.example.demo.service.impl;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductOutput;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductOutput getAllProductDTOByPaging(int page, int limit, String keyword, String sortBy, int categoryId) {

        ProductOutput result = new ProductOutput();
        result.setPage(page);
        Pageable pageable =null;

        switch (sortBy){
            case "none":
                pageable = PageRequest.of(page -1 , limit);
                break;
            case "increase_price":
                pageable = PageRequest.of(page -1 , limit, Sort.by("price").ascending());
                break;
            case "decrease_price":
                pageable = PageRequest.of(page -1 , limit, Sort.by("price").descending());
                break;
            case "increase_name":
                pageable = PageRequest.of(page -1 , limit, Sort.by("name").ascending());
                break;
            case "decrease_name":
                pageable = PageRequest.of(page -1 , limit, Sort.by("name").descending());
                break;
        }
        if(pageable == null){
            return  result;
        }
        List<ProductDTO> listProductDTOS = productRepository.getAllProductDTO(keyword, categoryId, pageable);
        result.setListProductDTOS(listProductDTOS);

        if(keyword.length() == 0){
            result.setTotalPage((int) Math.ceil((double) ((int) productRepository.findProductByCategoryId(categoryId).size()) / limit));
        }else{
            int index = getLengthOfListSearch(keyword);
            if(index == -1){
                return result;
            }
            result.setTotalPage((int) Math.ceil((double) index / limit));
        }

        return result;
    }


    @Override
    public int getLengthOfListSearch(String keyword){
        List<Product>listProductAfterSearch =productRepository.findByNameContaining(keyword);
        if(listProductAfterSearch.isEmpty()){
            return -1;
        }
        return listProductAfterSearch.size();
    }

}
