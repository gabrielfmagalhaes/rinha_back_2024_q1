package com.gabriel.rinha.resource;

import com.gabriel.rinha.dto.NovaTransacaoRequest;
import com.gabriel.rinha.dto.NovaTransacaoResponse;
import com.gabriel.rinha.repository.ClienteRepository;
import com.gabriel.rinha.repository.TransacaoRepository;

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
public class TransactionResource {

    @Inject
    ClienteRepository clienteRepository;
    
    @Inject
    TransacaoRepository transacaoRepository;

    //Qualquer insercao de cache, vai gerar uma inconsistencia eventual
    //Seria necessario inserir primeiro no cache -> banco de dados

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/transacoes")
    @Transactional
    public Uni<Response> createTransaction(Long id, NovaTransacaoRequest request) {
        var errorMessage = request.validateFields();

        if (errorMessage != null) {
            return Uni.createFrom().item(Response.status(422).build());
        }

        return clienteRepository.findById(id)
            .onItem().ifNotNull().transformToUni(cliente -> {
                var clienteAtt = cliente.crebito(request);

                return clienteRepository.persist(clienteAtt)
                    .map(updated -> Response.status(Response.Status.OK)
                        .entity(new NovaTransacaoResponse(updated.limite, updated.saldo))
                        .build());
            })
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity("Cliente não encontrado com o id " + id)::build);
    }

    
    // TODO adicionar Response DTO
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/extrato")
    public Uni<Response> getTransactions(Long id) {
        return clienteRepository.findById(id)
            .onItem().ifNotNull().transform(account -> 
                Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o id " + account.id)
                    .build()
            )
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity("Cliente não encontrado com o id " + id)::build);
    }
}
