package com.example.demo.service;

import com.example.demo.dto.CartDTO;

public interface WelcomeService {
    boolean createVerificationCode(String phoneNumber);

    boolean verifiedCode(String code, String phoneNumber);
    String saveOrderPayment(CartDTO cartDTO);
}
