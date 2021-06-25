package com.skcc.account.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Builder
    public Account(Long id, String username, String name, String password, String mobile, String address, String scope, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
    	this.name = name;
    	this.password = password;
        this.mobile = mobile;
        this.address = address;
        this.scope = scope;
        this.createdAt = createdAt;
    }
}
