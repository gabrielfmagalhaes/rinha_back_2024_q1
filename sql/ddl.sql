CREATE TABLE cliente (
    id integer PRIMARY KEY NOT NULL,
    saldo integer NOT NULL,
    limite integer NOT NULL
);

CREATE TABLE transacao (
    id SERIAL PRIMARY KEY,
    clienteId integer NOT NULL
    valor integer NOT NULL,
    descricao varchar(10) NOT NULL,
    efetuadaEm timestamp NOT NULL,
);

CREATE INDEX fk_transacao_clienteid ON transacao
(
    clienteId ASC
);