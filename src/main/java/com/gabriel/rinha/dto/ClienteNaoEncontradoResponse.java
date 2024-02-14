package com.gabriel.rinha.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ClienteNaoEncontradoResponse (
    String message
) {
    
    public static ClienteNaoEncontradoResponse novo(Long id) {
        return new ClienteNaoEncontradoResponse("Cliente não encontrado com o id " + id);
    }
}
