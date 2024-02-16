package com.gabriel.rinha.model;

import java.util.List;

import com.gabriel.rinha.dto.NovaTransacaoRequest;
import com.gabriel.rinha.dto.exceptions.SaldoInsuficienteException;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity(name = "clientes")
@Cacheable
public class Cliente {
    @Id 
    private Long id;
    private Integer saldo;
    private Integer limite;
    
    @OneToMany(mappedBy = "clienteId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private List<Transacao> transacoes;

    public Cliente() {
    }

    public Cliente crebito(NovaTransacaoRequest request) {
        var valor = request.valorToInteger();

        if (request.tipo().equals("c")) {
            this.saldo += request.valorToInteger();

            return this;
        }

        if (valor > (this.saldo + this.limite)) {
            throw new SaldoInsuficienteException(saldo, limite);
        }
    
        this.saldo -= valor;
         
        return this;

    }

    public Cliente updateWithTransactions(List<Transacao> transacoes) {
        this.transacoes = transacoes;

        return this;
    }
    
    public Long getId() {
        return id;
    }
    
    public Integer getSaldo() {
        return saldo;
    }

    public Integer getLimite() {
        return limite;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

}
