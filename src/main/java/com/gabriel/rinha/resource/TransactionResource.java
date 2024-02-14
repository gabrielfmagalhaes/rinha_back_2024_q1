package com.gabriel.rinha.resource;

import com.gabriel.rinha.dto.ClienteNaoEncontradoResponse;
import com.gabriel.rinha.dto.ExtratoResponse;
import com.gabriel.rinha.dto.NovaTransacaoRequest;
import com.gabriel.rinha.dto.NovaTransacaoResponse;
import com.gabriel.rinha.model.Transacao;
import com.gabriel.rinha.repository.ClienteRepository;
import com.gabriel.rinha.repository.TransacaoRepository;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    ClienteRepository clienteRepository;
    
    @Inject
    TransacaoRepository transacaoRepository;

    private static final String TRANSACAO_ERROR = "Não foi possível realizar a sua transação\n";

    private static final String EXTRATO_ERROR = "Não foi possível obter o extrato\n";

    @POST
    @Path("{id}/transacoes")
    @WithTransaction
    public Uni<Response> createTransaction(Long id, NovaTransacaoRequest request) {
        var errorMessage = request.validateFields();

        if (errorMessage != null) {
            return Uni.createFrom().item(Response.status(422)
                .entity(errorMessage)
                .build());
        }

        if (id < 1 || id > 5) {
            return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND)
                .entity(ClienteNaoEncontradoResponse.novo(id))
                .build());
        }

        return clienteRepository.findById(id, LockModeType.PESSIMISTIC_WRITE)
            .onItem().ifNotNull().transformToUni(cliente -> {
                var clienteAtualizado = cliente.crebito(request);
                var novaTransacao = Transacao.novo(request, id);

                return Uni.combine().all()
                    .unis(transacaoRepository.persist(novaTransacao), clienteRepository.persist(clienteAtualizado))
                    .asTuple()
                    .map(tuple -> Response.status(Response.Status.OK)
                        .entity(new NovaTransacaoResponse(tuple.getItem2().getLimite(), tuple.getItem2().getSaldo()))
                        .build());
            })
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity(ClienteNaoEncontradoResponse.novo(id))::build)
            .onFailure().recoverWithItem((ex) -> { 
                return Response.status(422)
                    .entity(TRANSACAO_ERROR + ex.getMessage())
                    .build();
            });
    }

    @GET
    @Path("{id}/extrato")
    public Uni<Response> getTransactions(Long id) {    
        if (id < 1 || id > 5) {
            return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND)
                .entity(ClienteNaoEncontradoResponse.novo(id))
                .build());
        }

        return clienteRepository.findById(id)
            .onItem().ifNotNull().transformToUni(cliente -> 
                transacaoRepository.findExtratoById(id)
                    .onItem().transform(transacoes ->
                        cliente.updateWithTransactions(transacoes)
                    )
                    .map(updatedCliente -> Response.status(Response.Status.OK)
                        .entity(ExtratoResponse.novoExtratoResponse(updatedCliente))
                        .build())
            )
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)
                .entity(ClienteNaoEncontradoResponse.novo(id))::build)
            .onFailure().recoverWithItem((ex) -> { 
                return Response.status(422)
                    .entity(EXTRATO_ERROR + ex.getMessage())
                    .build();
            });
    }
}
