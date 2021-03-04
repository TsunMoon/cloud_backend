package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Account")
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username",unique = true
//            , columnDefinition = "nvarchar(50)"
            ,nullable = false)
    private String username;

    @Column(name = "password"
//            , columnDefinition = "nvarchar(MAX)"
            , nullable = false)
    private String password;

    @Column(name = "fullname"
//            , columnDefinition = "nvarchar(50)"
    )
    private String fullname;

    @Column(name = "address"
//            , columnDefinition = "nvarchar(MAX)"
    )
    private String address;

    @Column(name = "phone"
//            , columnDefinition = "varchar(12)"
    )
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;



}
