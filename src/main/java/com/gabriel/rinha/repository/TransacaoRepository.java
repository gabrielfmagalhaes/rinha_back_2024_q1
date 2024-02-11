package com.gabriel.rinha.repository;

import java.util.List;

import com.gabriel.rinha.model.Transacao;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransacaoRepository implements PanacheRepository<Transacao> {
    
    public Uni<List<Transacao>> findExtratoById(Long id) {
        return find("clienteId = ?1 ORDER BY efetuadaEm DESC", id)
            .range(0, 9)
            .list();

    }
}
