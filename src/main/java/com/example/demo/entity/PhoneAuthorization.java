package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "PhoneAuthorization")
@Table(name = "phone_authorization")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhoneAuthorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phone", nullable = false, unique = false
//            , columnDefinition = "varchar(20)"
    )
    private String phone;

    @Column(name = "creat_at", nullable = false)
    private Timestamp createAt;

    @Column(name = "expired_at", nullable = false)
    private Timestamp expiredAt;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @Column(name = "verification_code", nullable = false, unique = true
//            , columnDefinition = "varchar(10)"
    )
    private String verificationCode;
}
