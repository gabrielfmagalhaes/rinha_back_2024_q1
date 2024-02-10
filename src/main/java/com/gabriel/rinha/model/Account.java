package com.gabriel.rinha.model;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//garantir consistencia com lock pessimista
@Entity
// @Cacheable
public class Account {
    @Id 
    public String id;
    public Integer saldo;
    public Integer limite;
    
    //TODO adicionar ManyToOne
    public Transaction transaction;

    private Account debit(Integer valor) {
        //fazer um cache do limite
        //com cache do limite, da pra verificar antes de fazer o find

        this.saldo = (valor * -1) - this.saldo;

        if (valor > this.limite) {
            //throw error
        }
        
        return this;
    }

    //checar possibilidade de deixar isso na controller direto
    private Account credit(Integer valor) {
        this.saldo += valor;

        return this;
    }

    public Account crebito(Integer valor, String tipo, String desc) {
        this.transaction = Transaction.create(this.id, valor, tipo, desc);

        if (tipo.equals("c")) {
            return this.debit(valor);
        }

        return this.credit(valor);
    }
}
