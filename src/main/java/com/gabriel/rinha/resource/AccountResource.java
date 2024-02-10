package com.gabriel.rinha.resource;

import com.gabriel.rinha.model.Account;
import com.gabriel.rinha.model.Transaction;
import com.gabriel.rinha.repository.AccountRepository;
import com.gabriel.rinha.repository.TransactionRepository;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("clientes")
public class AccountResource {

    @Inject
    AccountRepository accountRepository;
    
    @Inject
    TransactionRepository transactionRepository;

    //Qualquer inserção de cache, vai gerar uma inconsistencia eventual
    //Seria necessario inserir primeiro no cache -> banco de dados

    //TODO adicionar DTO
    //TODO adicionar Response DTO
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/transacoes")
    @Transactional
    public Uni<Response> createTransaction(Long id, 
    Integer valor, String tipo, String desc) {
        return accountRepository.findById(id)
            .onItem().ifNotNull().transformToUni(account -> {
                var newAccount = account.crebito(valor, tipo, desc);

                return accountRepository.persist(newAccount)
                    .map(updated -> Response.status(Response.Status.NOT_FOUND)
                    .entity("Transaction approved to id " + id)
                    .build());
            })
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity("Account not found with given id " + id)::build);
    }

    
    //TODO adicionar Response DTO
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/extrato")
    public Uni<Response> getTransactions(Long id) {
        return accountRepository.findById(id)
            .onItem().ifNotNull().transform(account -> 
                Response.status(Response.Status.NOT_FOUND)
                    .entity("Account not found with given id " + account.id)
                    .build()
            )
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity("Account not found with given id " + id)::build);
    }
}
