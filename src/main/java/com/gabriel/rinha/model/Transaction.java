package com.gabriel.rinha.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

//essa tabela deve ser uma insertOnly
@Entity
public class Transaction {
    
    public Integer accountId;
    public Integer valor;
    public String tipo;
    public String desc;
    public LocalDateTime createdAt;

    public static Transaction create(Long id, Integer valor2, String tipo2, String desc2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
}
