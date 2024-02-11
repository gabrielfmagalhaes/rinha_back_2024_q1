package com.gabriel.rinha.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//garantir consistencia com lock pessimista
@Entity
// @Cacheable
public class Cliente {
    @Id 
    public String id;
    public Integer saldo;
    public Integer limite;
    
    //TODO adicionar ManyToOne
    public Transacao transacao;

    private Cliente debito(Integer valor) {
        //fazer um cache do limite
        //com cache do limite, da pra verificar antes de fazer o find

        this.saldo = (valor * -1) - this.saldo;

        if (valor > this.limite) {
            //throw error
        }
        
        return this;
    }

    public Cliente crebito(NovaTransacaoRequest request) {
        this.transacao = Transacao.create(request);

        if (request.tipo().equals("d")) {
            return this.debito(request.valorToInteger());
        }

        this.saldo += request.valorToInteger();

        return this;
    }
}
