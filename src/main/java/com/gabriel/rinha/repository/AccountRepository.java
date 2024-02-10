package com.gabriel.rinha.repository;

import com.gabriel.rinha.model.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    
}
