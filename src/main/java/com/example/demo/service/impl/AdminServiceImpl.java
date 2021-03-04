package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.*;
import com.example.demo.service.AdminService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PriceByDateRepository priceByDateRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;


    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Override
    public ProductDTO getProductById(int id) {
        return productRepository.findProductById(id);
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        List<Category> listCategories = categoryRepository.findCategoryByName(productDTO.getCategory());
        if(listCategories.isEmpty()){
            return null;
        }
        if(productDTO.getName().isEmpty() || productDTO.getQuantity() == 0 || productDTO.price == 0 || productDTO.getImage().isEmpty()){
            return null;
        }
        newProduct.setCategory(listCategories.get(0));
        newProduct.setName(productDTO.getName());
        newProduct.setImage(productDTO.getImage());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setUsed(true);

       Product _product = productRepository.save(newProduct);

        PriceByDate newPriceByDate = new PriceByDate();
        newPriceByDate.setPrice(productDTO.getPrice());
        newPriceByDate.setProduct(_product);
        newPriceByDate.setIsUsed(true);
        newPriceByDate.setCreateAt(new Date(System.currentTimeMillis()));

        priceByDateRepository.save(newPriceByDate);
        return productMapper.productToDTO(_product);
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Product updateProduct = new Product();
        updateProduct = productRepository.findById(id).get();

        if(updateProduct == null){
            return null;
        }
        if(productDTO.getName().isEmpty() || productDTO.getQuantity() == 0 || productDTO.price == 0 || productDTO.getImage().isEmpty()){
            return null;
        }
        List<Category> listCategories = categoryRepository.findCategoryByName(productDTO.getCategory());
        if(listCategories.isEmpty()){
            return null;
        }
        List<PriceByDate> listPriceByDates = priceByDateRepository.findByProductId(id);

        // Dò trong list price theo product id, nếu giống với price trong danh sách thì đổi trạng
        // thái thành true còn ngược lại thì false
        int countSame = 0;
        for(PriceByDate priceByDate : listPriceByDates){

            if(priceByDate.getPrice() == productDTO.getPrice()){
                priceByDate.setIsUsed(true);
                countSame ++;
            }else {
                priceByDate.setIsUsed(false);
                priceByDateRepository.save(priceByDate);
            }
        }

        updateProduct.setName(productDTO.getName());
        updateProduct.setImage(productDTO.getImage());
        updateProduct.setQuantity(productDTO.getQuantity());
        updateProduct.setCategory(listCategories.get(0));

        Product _updateProduct = productRepository.save(updateProduct);

        if(countSame == 0){
            PriceByDate newPriceByDate = new PriceByDate();
            newPriceByDate.setPrice(productDTO.getPrice());
            newPriceByDate.setCreateAt(new Date(System.currentTimeMillis()));
            newPriceByDate.setIsUsed(true);
            newPriceByDate.setProduct(_updateProduct);
            priceByDateRepository.save(newPriceByDate);
        }

        return productMapper.productToDTO(_updateProduct);
    }

    @Override
    public String deleteProduct(int id) {
        try{
            Product deleteProduct = productRepository.findById(id).get();
            deleteProduct.setUsed(false);
            productRepository.save(deleteProduct);
            return "SUCCESS";
        }catch(Exception ex){
            return "ERROR";
        }
    }

    @Override
    public String updateIsUsedProduct(int id) {
        try{
            Product updateAgainProduct = productRepository.findById(id).get();
            updateAgainProduct.setUsed(true);
            productRepository.save(updateAgainProduct);
            return "SUCCESS";
        }catch(Exception ex){
            return "ERROR";
        }
    }



    @Override
    public List<OrderHistoryResponse> getAllOrderHistory() {

        // 1. Lấy tất cả các Order
        List<Order> listOrders = orderRepository.findAll();

        List<OrderHistoryResponse> listOrderHistoryResponses = new ArrayList<>();

        for(Order eachOrder : listOrders){
            List<OrderDetail> listOrderDetails = eachOrder.getListOrderDetails();

            List<OrderHistoryDetail> listHistoryDetails = new ArrayList<>();
            for(OrderDetail eachDetail : listOrderDetails){
                OrderHistoryDetail _orderHistoryDetail = new OrderHistoryDetail();
                _orderHistoryDetail.setName(eachDetail.getProduct().getName());
                _orderHistoryDetail.setAmount(eachDetail.getAmount());
                _orderHistoryDetail.setPriceDetail(eachDetail.getPriceDetail());
                _orderHistoryDetail.setCategory(eachDetail.getProduct().getCategory().getName());
                _orderHistoryDetail.setImage(eachDetail.getProduct().getImage());

                listHistoryDetails.add(_orderHistoryDetail);
            }


            OrderHistoryResponse newOrderHistory = new OrderHistoryResponse();
            newOrderHistory.setOrderId(eachOrder.getId());
            newOrderHistory.setCreateAt(eachOrder.getCreateAt());
            newOrderHistory.setPhoneNumber(eachOrder.getPhone());
            newOrderHistory.setListOrderHistoryDetails(listHistoryDetails);

            listOrderHistoryResponses.add(newOrderHistory);
        }
        return listOrderHistoryResponses;
    }

    @Override
    public List<OrderHistoryResponse> searchOrderHistory(String phoneNumber) {
        List<OrderHistoryResponse> listOrderHistoryResponses = getAllOrderHistory();
        List<OrderHistoryResponse> _listOrderHistoryResponses =  new ArrayList<>();

        for(OrderHistoryResponse eachOrder : listOrderHistoryResponses){
            if(eachOrder.getPhoneNumber().trim().contains(phoneNumber.trim())){
                _listOrderHistoryResponses.add(eachOrder);
            }
        }

        return _listOrderHistoryResponses;
    }

    @Override
    public ThreeInformationDTO getThreeInformation() {
        try {
            ThreeInformationDTO newThreeInformation = new ThreeInformationDTO();
            newThreeInformation.setNumberOrder(orderRepository.findAll().size());
            newThreeInformation.setNumberDrink(productRepository.findProductByCategoryId(1).size());
            newThreeInformation.setNumberFood(productRepository.findProductByCategoryId(2).size());
            return newThreeInformation;
        }catch(Exception ex){
            return null;
        }

    }
}
