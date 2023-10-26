package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.endereco.Endereco;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "unidade_endereco",
        joinColumns =
                { @JoinColumn(name = "unid_id", referencedColumnName = "unid_id")},
            inverseJoinColumns =
                { @JoinColumn(name = "end_id", referencedColumnName = "end_id")}
    )
    private Endereco endereco;

    public Unidade(String nome, String sigla, Endereco endereco) {
        this.nome = nome;
        this.sigla = sigla;
        this.endereco = endereco;
    }

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", nome, sigla);
    }
}
