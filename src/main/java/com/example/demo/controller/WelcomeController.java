package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Account;
import com.example.demo.entity.Category;
import com.example.demo.entity.PhoneAuthorization;
import com.example.demo.entity.Role;
import com.example.demo.global.ConstantVariable;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.repository.*;
import com.example.demo.service.ProductService;
import com.example.demo.service.WelcomeService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ConstantVariable.WELCOME)
@CrossOrigin
public class WelcomeController {

    @Autowired
    ProductService productService;

    @Autowired
    WelcomeService welcomeService;

    @Autowired
    PhoneAuthorizationRepository phoneAuthorizationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = "/get_all_product")
    public ProductOutput getAllProductDTOByPaging(@RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam("keyword") String keyword,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("categoryId") int categoryId){

       return productService.getAllProductDTOByPaging(page, limit, keyword, sortBy, categoryId);
    }


    @PostMapping(value = "/create_verification_code")
    public ResponseEntity<HttpStatus> createVerificationCode(@RequestParam("phone_number") String phoneNumber) {
        try {
            if(welcomeService.createVerificationCode(phoneNumber)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/enter_verification_code")
    public ResponseEntity<HttpStatus> enterVerificationCode(
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("code") String code){

        try{
           if(welcomeService.verifiedCode(code, phoneNumber)){
             return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lưu hóa đơn
    @PostMapping(value = "/save_order_payment")
    public ResponseEntity<HttpStatus> saveOrderPayment(@RequestBody CartDTO cartDTO) {
        try {
            if (welcomeService.saveOrderPayment(cartDTO) == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cái này chỉ để test thôi thêm mới tài khoản thôi nha
    @PostMapping(value = "/create_new_admin_account")
    public ResponseEntity<HttpStatus> createNewAdminAccount (@RequestBody AccountRequestDTO accountRequestDTO){
        try {
            Account _account = new Account();
            _account.setAddress(accountRequestDTO.getAddress());
            _account.setFullname(accountRequestDTO.getFullname());
            _account.setPassword(accountRequestDTO.getPassword());
            _account.setPhone(accountRequestDTO.getPhone());
            _account.setUsername(accountRequestDTO.getUsername());
            _account.setRole(roleRepository.findById(accountRequestDTO.getRoleId()).get());
            accountRepository.save(_account);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Tạo mới role
    @PostMapping(value = "/create_new_role")
    public ResponseEntity<HttpStatus> createNewRole (@RequestBody RoleDTO roleDTO){
        try {
            Role _role = new Role();
            _role.setName(roleDTO.getName());
            roleRepository.save(_role);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Tạo mới category
    @PostMapping(value = "/create_new_category")
    public ResponseEntity<HttpStatus> createNewCategory (@RequestBody RoleDTO roleDTO){
        try {
            Category _category = new Category();
            _category.setName(roleDTO.getName());
            categoryRepository.save(_category);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
