package com.gabriel.rinha.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gabriel.rinha.model.Cliente;
import com.gabriel.rinha.model.Transacao;

public record ExtratoResponse (
    ClienteItemResponse saldo,
    List<HistoricoTransacaoItemResponse> ultimas_transacoes
) {
    public static ExtratoResponse novoExtratoResponse(Cliente cliente) {
        List<HistoricoTransacaoItemResponse> historicoTransacoes = new ArrayList<>(cliente.getTransacoes().size());

        for (Transacao transacao : cliente.getTransacoes()) {
            var historicoTransacao = new HistoricoTransacaoItemResponse(
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getDescricao(),
                transacao.getEfetuadaEm()
            );

            historicoTransacoes.add(historicoTransacao);
        }

        return new ExtratoResponse(
            new ClienteItemResponse(cliente.getSaldo(), 
                LocalDateTime.now(), 
                cliente.getLimite()),
            historicoTransacoes);
    }
}
