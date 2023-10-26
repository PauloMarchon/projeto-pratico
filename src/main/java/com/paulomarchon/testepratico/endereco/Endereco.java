package com.paulomarchon.testepratico.endereco;

import com.paulomarchon.testepratico.cidade.Cidade;
import com.paulomarchon.testepratico.unidade.Unidade;
import jakarta.persistence.*;

@Entity
@Table(name = "endereco", schema = "enderecos")
public class Endereco {
    @Id
    @SequenceGenerator(
            name = "endereco_id_seq",
            sequenceName = "endereco_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "endereco_id_seq"
    )
    @Column(name = "end_id")
    private Integer id;
    @Column(name = "end_tipo_logradouro")
    private String tipoLogradouro;
    @Column(name = "end_logradouro")
    private String logradouro;
    @Column(name = "end_numero")
    private int numero;
    @Column(name = "end_bairro")
    private String bairro;
    @ManyToOne
    @JoinColumn(name = "cid_id", referencedColumnName = "cid_id")
    private Cidade cidade;
    @OneToOne(mappedBy = "endereco")
    private Unidade unidade;

    public Endereco(String tipoLogradouro, String logradouro, int numero, String bairro, Cidade cidade) {
        this.tipoLogradouro = tipoLogradouro.toUpperCase();
        this.logradouro = logradouro.toUpperCase();
        this.numero = numero;
        this.bairro = bairro.toUpperCase();
        this.cidade = cidade;
    }

    public Endereco() {
    }

    public Integer getId() {
        return id;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s, %d %s, %s", tipoLogradouro, logradouro, numero, bairro, cidade.getUf()
        );
    }
}
