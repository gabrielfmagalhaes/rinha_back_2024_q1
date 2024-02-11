package com.gabriel.rinha.dto;

import java.time.LocalDateTime;

public record ClienteItemResponse(
    Integer total,
    LocalDateTime data_extrato,
    Integer limite
) {
    
}
