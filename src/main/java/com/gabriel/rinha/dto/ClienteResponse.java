package com.gabriel.rinha.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
    Integer total,
    LocalDateTime data_extrato,
    Integer limite
) {
    
}
