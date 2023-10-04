CREATE SCHEMA IF NOT EXISTS unidades;

CREATE TABLE unidades.unidade(
    unid_id serial not null,
    unid_nome varchar(200) not null,
    unid_sigla varchar(20) not null,
    CONSTRAINT pk_unidade PRIMARY KEY (unid_id)
);