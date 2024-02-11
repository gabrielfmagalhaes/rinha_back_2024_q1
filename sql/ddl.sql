CREATE TABLE clientes (
    id integer PRIMARY KEY NOT NULL,
    nome varchar(25) NOT NULL,
    saldo integer NOT NULL,
    limite integer NOT NULL
);

CREATE TABLE transacoes (
    id SERIAL PRIMARY KEY,
    clienteId integer NOT NULL,
    tipo char(1) NOT NULL,
    valor integer NOT NULL,
    descricao varchar(10) NOT NULL,
    efetuadaEm timestamp NOT NULL,
    FOREIGN KEY (clienteId) REFERENCES clientes (id)
);

-- Se não rolar de manter uma ORM performatica
-- alterar para queries isoladas com apoio de funções

-- CREATE INDEX fk_transacao_clienteid ON transacoes
-- (
--     clienteId ASC
-- );

