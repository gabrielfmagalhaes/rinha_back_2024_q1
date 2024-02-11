package com.gabriel.rinha.model;

import java.util.List;

import org.hibernate.annotations.Cache;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

//garantir consistencia com lock pessimista
@Entity
// @Cacheable
public class Cliente {
    @Id 
    public String id;
    public Integer saldo;
    public Integer limite;
    
    /* TODO testar se é possivel 
        1 - Adicionar transacao sem se preocupar com transacoes
        2 - Retornar transacoes sem se preocupar com transacao
        3 - Checar algo sobre projection
    */
    // public Transacao transacao;

    // // TODO adicionar ManyToOne

    // @ManyToOne
    // public List<Transacao> transacoes;

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
        // this.transacao = Transacao.create(request);

        if (request.tipo().equals("d")) {
            return this.debito(request.valorToInteger());
        }

        this.saldo += request.valorToInteger();

        return this;
    }
}
