package com.skcc.accountbank.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class AccountBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long accountId;

    @Column(length = 255)
    private String bankName;

    @Column(length = 255)
    private String bankNumber;

    @Column(length = 255)
    private String active;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Builder
    public AccountBank(Long id, Long accountId, String bankName, String bankNumber, String active, LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
    	this.bankName = bankName;
    	this.bankNumber = bankNumber;
        this.active = active;
        this.createdAt = createdAt;
    }
}
