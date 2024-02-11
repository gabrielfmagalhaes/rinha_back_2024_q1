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
    public ExtratoResponse novoExtratoResponse(Cliente cliente) {
        List<HistoricoTransacaoItemResponse> historicoTransacoes = new ArrayList<>(cliente.transacoes.size());

        for (Transacao transacao : cliente.transacoes) {
            var historicoTransacao = new HistoricoTransacaoItemResponse(
                transacao.valor,
                transacao.tipo,
                transacao.descricao,
                transacao.efetuadaEm
            );

            historicoTransacoes.add(historicoTransacao);
        }

        return new ExtratoResponse(
            new ClienteItemResponse(cliente.saldo, 
                LocalDateTime.now(), 
                cliente.limite),
            historicoTransacoes);
    }
}
