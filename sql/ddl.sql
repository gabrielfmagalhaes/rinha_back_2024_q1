CREATE TABLE clientes (
    id integer PRIMARY KEY NOT NULL,
    nome varchar(25) NOT NULL,
    saldo integer NOT NULL,
    limite integer NOT NULL
);

CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    clienteId integer NOT NULL,
    valor integer NOT NULL,
    descricao varchar(10) NOT NULL,
    efetuadaEm timestamp NOT NULL
);

CREATE INDEX fk_transacao_clienteid ON transacoes
(
    clienteId ASC
);