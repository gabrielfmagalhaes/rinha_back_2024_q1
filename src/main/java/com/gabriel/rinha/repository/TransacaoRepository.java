package com.gabriel.rinha.repository;

import com.gabriel.rinha.model.Transacao;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransacaoRepository implements PanacheRepository<Transacao> {
    
    //TODO adicionar find com orderBy
}
