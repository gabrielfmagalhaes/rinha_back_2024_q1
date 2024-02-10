package com.gabriel.rinha.resource;

import com.gabriel.rinha.model.Account;
import com.gabriel.rinha.model.Transaction;
import com.gabriel.rinha.repository.AccountRepository;
import com.gabriel.rinha.repository.TransactionRepository;

import jakarta.inject.Inject;
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

    //Qualquer inserção de cache, vai gerar uma consistencia eventual
    //Seria necessario inserir primeiro no cache -> banco de dados
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/transacoes")
    public Response createTransaction(Long id, 
    Integer valor, String tipo, String desc) {
        //cache
        var account = accountRepository.findById(id);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } 

        //checar para deixar reativo
        //ambos podem ocorrer em paralelo, mas precisa de um transaction manager
        if (tipo.equals("d")) {
            account = account.debit(valor);
            var transaction = Transaction.create(id, valor, tipo, desc);

            accountRepository.persist(account);
            transactionRepository.persist(transaction);
        } else {
            account.credit(valor);
            var transaction = Transaction.create(id, valor, tipo, desc);

            accountRepository.persist(account);
            transactionRepository.persist(transaction);
        }

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/extrato")
    public String getTransactions(String id) {
        return "Hello from 2";
    }
}
