package com.example.demo.service.impl;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.PhoneAuthorization;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PhoneAuthorizationRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.WelcomeService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    private int EXPIRED_TIME = 300000;
    private String ACCOUNT_SID = "AC09e33d1014c54d9dbc2f302ca6bc3ff1";
    private String AUTH_TOKEN = "870f44d37f7b46d1d04738a0e9aa3378";
    private String TRIAL_NUMBER = "+16033927171";

    @Autowired
    PhoneAuthorizationRepository phoneAuthorizationRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    // Tạo và gửi tin nhắn verification code
    @Override
    public boolean createVerificationCode(String phoneNumber) {

        if(phoneNumber.isEmpty()){
            return false;
        }
        try {
            String subPhone = phoneNumber.substring(1, phoneNumber.length());
            int validPhone = Integer.parseInt(subPhone);
        }catch(Exception ex){
            return false;
        }

        PhoneAuthorization duplicatePhone = phoneAuthorizationRepository.findPhoneAuthorizationByPhone(phoneNumber);
        String randomNumber = randomVerificationCode(0,99999).trim();
        if(duplicatePhone != null){
            duplicatePhone.setVerificationCode(randomNumber);
            duplicatePhone.setCreateAt(new Timestamp(System.currentTimeMillis()));
            duplicatePhone.setExpiredAt(new Timestamp(System.currentTimeMillis() + EXPIRED_TIME));
            phoneAuthorizationRepository.save(duplicatePhone);
        }else {
            PhoneAuthorization _phoneAuthorization = new PhoneAuthorization();
            _phoneAuthorization.setPhone(phoneNumber);
            _phoneAuthorization.setCreateAt(new Timestamp(System.currentTimeMillis()));
            _phoneAuthorization.setExpiredAt(new Timestamp(System.currentTimeMillis() + EXPIRED_TIME));
            _phoneAuthorization.setAvailable(true);
            _phoneAuthorization.setVerificationCode(randomVerificationCode(0,99999));
            phoneAuthorizationRepository.save(_phoneAuthorization);
        }
  //      sendMessage(randomNumber, convertPhone(phoneNumber));
        return true;
    }

    @Override
    public boolean verifiedCode(String code, String phoneNumber) {
        PhoneAuthorization verifiedPhone = phoneAuthorizationRepository.findPhoneAuthorizationByPhone(phoneNumber);
        if(verifiedPhone == null){
            return false;
        }
        if(code.trim().equals(verifiedPhone.getVerificationCode().trim())){
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            int result = currentTime.compareTo(verifiedPhone.getExpiredAt());
            if(result <= 0){
                phoneAuthorizationRepository.delete(verifiedPhone);
                return true;
            }
        }
        return false;
    }

    private String randomVerificationCode(int min, int max){
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt() + "";
    }

    private void sendMessage(String code, String phoneNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(TRIAL_NUMBER),
               "Your code: " + code ).create();
    }

    private String convertPhone(String phoneNumber){
        String subPhone =  phoneNumber.substring(1,phoneNumber.length()).trim();
        String messPhone = "+84" + subPhone;
        return  messPhone;
    }


    @Override
    public String saveOrderPayment(CartDTO cartDTO) {
        if(cartDTO == null){
            return "FAILED";
        }

        //1. Lấy phoneNumber lưu vào Order
        String phoneNumber = cartDTO.getPhoneNumber();

        //2. Lấy ngày hiện tại cho creat_at lưu vào Order
        Date currentDate = new Date(System.currentTimeMillis());

        //3. Lấy ra list cart để lưu vào từng order detail
        List<CartItemDTO> listCartItemDTOS = cartDTO.getListCart();

        Order order =  new Order();
        order.setCreateAt(currentDate);
        order.setPhone(phoneNumber);
        Order _order = orderRepository.save(order);

        for(CartItemDTO eachItem : listCartItemDTOS){
            ProductDTO productDTO = productRepository.findProductById(eachItem.getProductId());

            if(eachItem.getAmount() > productDTO.getQuantity()){
                orderRepository.delete(_order);
                return "FAILED";
            }



            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setAmount(eachItem.getAmount());
            orderDetail.setPriceDetail(productDTO.getPrice());
            orderDetail.setProduct(productRepository.findById(eachItem.getProductId()).get());
            orderDetail.setOrder(_order);
            orderDetailRepository.save(orderDetail);

            // lấy quantity của product
            Product product = productRepository.findById(eachItem.getProductId()).get();
            product.setQuantity(product.getQuantity() - eachItem.getAmount());
            // trừ quantity của product
            productRepository.save(product);
        }


        return "SUCCESS";
    }

}
