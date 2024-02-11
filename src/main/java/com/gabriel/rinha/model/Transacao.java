package com.gabriel.rinha.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

//essa tabela deve ser uma insertOnly
@Entity
// @Cacheable
public class Transacao {
    
    public Integer clienteId;
    public Integer valor;
    public String tipo;
    public String desc;

    @CreationTimestamp
	@Column(nullable = false, updatable = false)
    public LocalDateTime efetuadaEm;

    public static Transacao create(NovaTransacaoRequest request) {
        return new Transacao();
    }
}
