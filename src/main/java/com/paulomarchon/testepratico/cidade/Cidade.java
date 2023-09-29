package com.paulomarchon.testepratico.cidade;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade", schema = "cidades")
public class Cidade {
    @Id
    @SequenceGenerator(
            name = "cidade_id_seq",
            sequenceName = "cidade_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cidade_id_seq"
    )
    @Column(name = "cid_id")
    private Integer id;
    @Column(name = "cid_nome")
    private String nome;
    @Column(name = "cid_uf")
    private String uf;

    public Cidade(String nome, String uf) {
        this.nome = nome.toUpperCase();
        this.uf = uf.toUpperCase();
    }

    public Cidade() {

    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", nome, uf);
    }
}
