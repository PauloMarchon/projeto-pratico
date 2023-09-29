CREATE SCHEMA IF NOT EXISTS cidades;

CREATE TABLE cidades.cidade (
    cid_id serial not null,
    cid_nome varchar(200) not null,
    cid_uf varchar(2) not null,
    CONSTRAINT pk_cidade PRIMARY KEY (cid_id)
);

INSERT INTO cidades.cidade(cid_nome, cid_uf)
VALUES
    ('RIO DE JANEIRO','RJ'),
    ('SAO PAULO','SP'),
    ('SALVADOR','BH'),
    ('BRASILIA','DF'),
    ('GRAMADO','RS'),
    ('MANAUS','AM'),
    ('CUIABA','MT');