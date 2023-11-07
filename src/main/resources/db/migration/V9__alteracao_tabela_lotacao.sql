ALTER TABLE lotacoes.lotacao
    ADD CONSTRAINT fk_pessoa FOREIGN KEY (pes_id) REFERENCES pessoas.pessoa(pes_id);

ALTER TABLE lotacoes.lotacao
    ADD CONSTRAINT fk_unidade FOREIGN KEY (unid_id) REFERENCES unidades.unidade(unid_id);
