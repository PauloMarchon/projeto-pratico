package com.paulomarchon.testepratico.unidade;

import jakarta.persistence.*;

@Entity
@Table(name = "unidade", schema = "unidades")
public class Unidade {
    @Id
    @SequenceGenerator(
            name = "unidade_unid_id_seq",
            sequenceName = "unidade_unid_id_seq",
            schema = "unidades",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unidade_unid_id_seq"
    )
    @Column(name = "unid_id")
    private Integer id;
    @Column(name = "unid_nome")
    private String nome;
    @Column(name = "unid_sigla")
    private String sigla;

    public Unidade(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public Unidade() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", nome, sigla);
    }
}
