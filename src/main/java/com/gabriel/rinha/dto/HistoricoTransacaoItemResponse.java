package com.gabriel.rinha.dto;

import java.time.LocalDateTime;

public record HistoricoTransacaoItemResponse (
    Integer valor,
    String tipo,
    String descricao,
    LocalDateTime efetuadaEm
) {

}
