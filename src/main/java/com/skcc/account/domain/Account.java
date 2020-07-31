package com.skcc.account.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, nullable = false)
    private String username ;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String password;
    
    @Column(length = 255, nullable = false)
    private String mobile;
    
    @Column(length = 255, nullable = false)
    private String address;
    
    @Column(length = 255, nullable = false)
    private String scope;
    
    @Builder
    public Account(Long id, String username, String name, String password, String mobile, String address, String scope) {
        this.id = id;
        this.username = username;
    	this.name = name;
    	this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.scope = scope;
    }
}
