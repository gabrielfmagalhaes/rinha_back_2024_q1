package com.gabriel.rinha.repository;

import com.gabriel.rinha.model.Transaction;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
    
    //TODO adicionar find com orderBy
}
