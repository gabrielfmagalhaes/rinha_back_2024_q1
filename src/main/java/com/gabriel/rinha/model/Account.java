package com.gabriel.rinha.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//garantir onsistencia com lock pessimista
@Entity
public class Account extends PanacheEntity {
    @Id 
    public String id;
    public Integer saldo;
    public Integer limite;

    public Account debit(Integer valor) {
        //fazer um cache do limite
        //com cache do limite, da pra verificar antes de fazer o find

        this.saldo = (valor * -1) - this.saldo;

        if (valor > this.limite) {
            //throw error
        }
        
        return this;
    }

    //checar possibilidade de deixar isso na controller direto
    public Account credit(Integer valor) {
        this.saldo += valor;

        return this;
    }
}
