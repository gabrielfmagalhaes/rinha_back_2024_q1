package com.gabriel.rinha.dto;

import java.time.LocalDateTime;

import com.gabriel.rinha.model.Transacao;

public record HistoricoTransacaoItemResponse (
    Integer valor,
    String tipo,
    String descricao,
    LocalDateTime efetuadaEm
) {
    
    public HistoricoTransacaoItemResponse novoHistorico(Transacao transacao) {
        return new HistoricoTransacaoItemResponse(
            transacao.valor,
            transacao.tipo,
            transacao.descricao,
            transacao.efetuadaEm
        );
    }
}
