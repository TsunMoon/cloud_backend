package com.example.demo.controller;

import com.example.demo.dto.LoginReqDTO;
import com.example.demo.dto.LoginResDTO;
import com.example.demo.global.ConstantVariable;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ConstantVariable.AUTHENTICATION)
@CrossOrigin
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "authenticate")
    public LoginResDTO authenticateAccount(@RequestBody LoginReqDTO loginReqDTO){
        return  authenticationService.login(loginReqDTO);
    }
}
