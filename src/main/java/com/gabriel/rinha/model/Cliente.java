package com.gabriel.rinha.model;

import java.util.List;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

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

    private Cliente debito(Integer valor) {
        if (valor > (this.saldo + this.limite)) {
            throw new RuntimeException(
                String.format("Saldo insuficiente\n" +
                    "Saldo Atual: %d\nLimite da conta: %d", 
                    this.saldo, this.limite));
        }
    
        this.saldo -= valor;
         
        return this;
    }

    public Cliente crebito(NovaTransacaoRequest request) {
        if (request.tipo().equals("d")) {
            return this.debito(request.valorToInteger());
        }

        this.saldo += request.valorToInteger();

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
