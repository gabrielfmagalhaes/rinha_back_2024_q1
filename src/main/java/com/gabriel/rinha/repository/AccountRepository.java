package com.gabriel.rinha.repository;

import com.gabriel.rinha.model.Account;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    
}
