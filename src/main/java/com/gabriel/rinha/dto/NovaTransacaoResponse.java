package com.gabriel.rinha.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record NovaTransacaoResponse (
    Integer limite,
    Integer saldo) {

}
