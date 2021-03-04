package com.example.demo.service.impl;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.LoginReqDTO;
import com.example.demo.dto.LoginResDTO;
import com.example.demo.entity.Account;
import com.example.demo.jwt.JwtUtils;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AuthenticationService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountRepository accountRepository;

    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Override
    public LoginResDTO login(LoginReqDTO requestLogin) {

        AccountDTO newAccount = accountMapper.accountToDTO(accountRepository.findAccountByUsername(requestLogin.getUsername()));

        if(newAccount == null){
            return LoginResDTO.createErrorResponse(LoginResDTO.Error.USERNAME_NOT_FOUND);
        }
        if(!newAccount.getPassword().equals(requestLogin.getPassword())){
            return LoginResDTO.createErrorResponse(LoginResDTO.Error.WRONG_PASSWORD);
        }
        String role = newAccount.getRole().getName();
        String token =jwtUtils.generateToken(newAccount.getUsername());
        String username = newAccount.getUsername();
        return LoginResDTO.createSuccessResponse(token, role, username);
    }
}
