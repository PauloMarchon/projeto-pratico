CREATE SCHEMA IF NOT EXISTS enderecos;

CREATE TABLE enderecos.endereco(
    end_id serial not null,
    end_tipo_logradouro varchar(50) not null,
    end_logradouro varchar(200) not null ,
    end_numero integer,
    end_bairro varchar(100) not null,
    cid_id integer not null ,
    CONSTRAINT pk_endereco PRIMARY KEY (end_id),
    CONSTRAINT fk_cidade FOREIGN KEY (cid_id) REFERENCES cidades.cidade(cid_id)
);

INSERT INTO enderecos.endereco(end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id)
VALUES
    ('RUA','FERREIRA VIANA',222,'FLAMENGO',1),
    ('RUA','PRACA DA BANDEIRA',520,'CENTRO',2),
    ('RUA','TOKAIA',56,'ITAPUA',3),
    ('QUADRA 1','SAUS',1324,'ASA SUL',4),
    ('AVENIDA','BORGES DE MEDEIROS',123,'CENTRO',5),
    ('RUA','21 DE JULHO',7,'LAGO AZUL',6),
    ('RUA','DEZOITO',678,'JARDIM NOVO MILENIO',7);