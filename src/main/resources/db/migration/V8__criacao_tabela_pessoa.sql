CREATE SCHEMA IF NOT EXISTS pessoas;

CREATE TABLE pessoas.pessoa(
    pes_id serial not null,
    pes_nome varchar(200) not null,
    pes_data_nascimento date not null,
    pes_sexo varchar(9) not null,
    pes_mae varchar(200) not null,
    pes_pai varchar(200) not null,
    CONSTRAINT pk_pessoa PRIMARY KEY (pes_id)
);
