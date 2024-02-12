package com.gabriel.rinha.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.gabriel.rinha.dto.NovaTransacaoRequest;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "transacoes")
@Cacheable
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long clienteId;

    private Integer valor;
    
    private String tipo;
    private String descricao;

    @CreationTimestamp
	@Column(nullable = false, updatable = false)
    private LocalDateTime efetuadaEm;

    public Transacao() {}
    
    public Transacao(Long clienteId, Integer valor, String tipo, String descricao) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Integer getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getEfetuadaEm() {
        return efetuadaEm;
    }

    public static Transacao novo(NovaTransacaoRequest request, Long clienteId) {
        var valorConvertido = request.valorToInteger();

        return new Transacao(
            clienteId,
            valorConvertido,
            request.tipo(),
            request.descricao());
    }

}
