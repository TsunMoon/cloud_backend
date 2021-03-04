package com.example.demo.service;

import com.example.demo.dto.LoginReqDTO;
import com.example.demo.dto.LoginResDTO;

public interface AuthenticationService {
    LoginResDTO login (LoginReqDTO loginReqDTO);
}
