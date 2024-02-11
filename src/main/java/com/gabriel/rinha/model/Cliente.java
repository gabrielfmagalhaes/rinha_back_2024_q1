package com.gabriel.rinha.model;

import java.util.ArrayList;
import java.util.List;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;


//TODO checar se rola manter esse cacheable

@Entity(name = "clientes")
@Cacheable
public class Cliente {
    @Id 
    private Long id;
    private Integer saldo;
    private Integer limite;
    
    /* TODO testar se é possivel 
        1 - Adicionar transacao sem se preocupar com transacoes
        2 - Retornar transacoes sem se preocupar com transacao
        3 - Checar algo sobre projection
    */
    
    @OneToMany(mappedBy = "clienteId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

    private Cliente debito(Integer valor) {
        //fazer um cache do limite
        //com cache do limite, da pra verificar antes de fazer o find
        if (valor > (this.saldo + this.limite)) {
            throw new RuntimeException(
                String.format("Saldo insuficiente\n" +
                    "Saldo Atual: %d\nLimite da conta: %d", 
                    this.saldo, this.limite));
        }
    
        // Update the saldo
        this.saldo -= valor;
         
        return this;
    }

    public Cliente crebito(NovaTransacaoRequest request) {
        var valorConvertido = request.valorToInteger();
        
        transacoes.add(new Transacao(
            this.id,
            valorConvertido,
            request.tipo(),
            request.descricao()));

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
