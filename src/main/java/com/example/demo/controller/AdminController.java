package com.example.demo.controller;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.OrderHistoryResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ThreeInformationDTO;
import com.example.demo.global.ConstantVariable;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ConstantVariable.ADMIN)
@CrossOrigin
public class AdminController {

    @Autowired
    AdminService adminService;


    @GetMapping()
    public String hellAdmin() {
        return "Hello Admin ";
    }

    //Lấy sản phẩm theo id để update
    @GetMapping(value = "/get_product_by_id/{id}")
    public ProductDTO getProductById(@PathVariable("id") int id) {
        return adminService.getProductById(id);
    }

    //Tạo mới sản phẩm
    @PostMapping(value = "/create_new_product")
    public ResponseEntity<ProductDTO> createNewProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO _productDTO = adminService.createNewProduct(productDTO);
            if (_productDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(_productDTO, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update_product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") int id, @RequestBody ProductDTO productDTO) {

        try {
            ProductDTO resultDTO = adminService.updateProduct(id, productDTO);
            if (resultDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete_product/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
        if (adminService.deleteProduct(id).equals("SUCCESS")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Cập nhật lại isUsued của Product
    @GetMapping(value = "/update_is_used_prouct/{id}")
    public ResponseEntity<HttpStatus> updateIsUsedProduct(@PathVariable("id") int id) {
        if (adminService.updateIsUsedProduct(id).equals("SUCCESS")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // Lấy tất cả order
    @GetMapping(value = "/get_all_order_history")
    public ResponseEntity<List<OrderHistoryResponse>> getAllOrderHistory() {
        try {
            List<OrderHistoryResponse> _listOrderHistoryResponses = adminService.getAllOrderHistory();
            if (_listOrderHistoryResponses.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(_listOrderHistoryResponses, HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API search order history
    @GetMapping(value = "/seach_order_history")
    public ResponseEntity<List<OrderHistoryResponse>> searchOrderHistory(@RequestParam("search_phone") String phoneNumber){
        try {
            List<OrderHistoryResponse> _listOrderHistoryResponses = adminService.searchOrderHistory(phoneNumber);
            if(_listOrderHistoryResponses.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(_listOrderHistoryResponses, HttpStatus.OK);
            }
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API lấy 3 thông số để hiện thị trên tranbg dashboard
    @GetMapping(value = "/get_three_information")
    public ResponseEntity<ThreeInformationDTO> getThreeInformation(){
        try{
            ThreeInformationDTO newThreeInformation = adminService.getThreeInformation();
            if( newThreeInformation == null){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(newThreeInformation, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
