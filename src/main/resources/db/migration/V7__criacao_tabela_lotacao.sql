CREATE SCHEMA IF NOT EXISTS lotacoes;

CREATE TABLE lotacoes.lotacao(
    lot_id serial not null,
    pes_id integer not null,
    unid_id integer not null,
    lot_data_lotacao date not null,
    lot_data_remocao date,
    lot_portaria varchar(100) not null ,
    CONSTRAINT pk_lotacao PRIMARY KEY (lot_id)
);