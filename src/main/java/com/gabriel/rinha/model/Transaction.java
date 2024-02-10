package com.gabriel.rinha.model;

import java.time.LocalDateTime;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;

//essa tabela deve ser uma insertOnly
@Entity
// @Cacheable
public class Transaction {
    
    public Integer accountId;
    public Integer valor;
    public String tipo;
    public String desc;
    public LocalDateTime createdAt;

    public static Transaction create(String id, Integer valor2, String tipo2, String desc2) {
        return new Transaction();
    }
}
