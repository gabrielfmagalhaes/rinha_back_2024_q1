package com.gabriel.rinha.dto.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(int saldo, int limite) {
        super("Saldo insuficiente\n" +
            "Saldo Atual: " + saldo + "\n" +
            "Limite da conta: " + limite);
    } 
}
