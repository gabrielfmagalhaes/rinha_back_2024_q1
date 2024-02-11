package com.gabriel.rinha.dto;

public record NovaTransacaoRequest(
    String valor,
    String tipo,
    String descricao) {
        

    public String validateFields() {
        if (descricao == null || descricao.trim().equals("") || descricao.length() > 10) {
            return "A descrição deve ser uma String de 1 a 10 caracteres";
        }

        if (tipo == null || !tipo.equals("c") || !tipo.equals("d")) {
            return "Deve informar 'c' para crédito ou 'd' para débito";
        }

        var valorConvertido = valorToInteger();

        if (valorConvertido == null || valorConvertido < 0) {
            return "Deve informar um valor inteiro positivo";
        }

        return null;
    }

    public Integer valorToInteger() {
        try {
            return Integer.parseInt(this.valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
