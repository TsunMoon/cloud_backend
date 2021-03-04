package com.example.demo.dto;

import com.example.demo.entity.Role;
import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO implements Serializable {
    public int id;
    public String username;
    public String password;
    public String fullname;
    public String address;
    public String phone;
    public Role role;


}
