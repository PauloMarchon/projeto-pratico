package com.paulomarchon.testepratico.lotacao;

import com.paulomarchon.testepratico.pessoa.Pessoa;
import com.paulomarchon.testepratico.unidade.Unidade;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "lotacao", schema = "lotacoes",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "lot_portaria")
        })
public class Lotacao {
    @Id
    @SequenceGenerator(
            name = "lot_id_seq",
            sequenceName = "lot_id_seq",
            schema = "lotacoes",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lot_id_seq"
    )
    @Column(name = "lot_id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "unid_id", nullable = false)
    private Unidade unidade;

    @Column(name = "lot_data_lotacao")
    private LocalDate dataLotacao;
    @Column(name = "lot_data_remocao")
    private LocalDate dataRemocao;
    @Column(name = "lot_portaria")
    private String portaria;

    public Lotacao() {
    }

    public Lotacao(Unidade unidade, Pessoa pessoa, LocalDate dataLotacao, String portaria) {
        this.unidade = unidade;
        this.pessoa = pessoa;
        this.dataLotacao = dataLotacao;
        this.portaria = portaria;
    }

    public Lotacao(Unidade unidade, Pessoa pessoa, LocalDate dataLotacao, LocalDate dataRemocao, String portaria) {
        this.unidade = unidade;
        this.pessoa = pessoa;
        this.dataLotacao = dataLotacao;
        this.dataRemocao = dataRemocao;
        this.portaria = portaria;
    }

    public Integer getId() {
        return id;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public LocalDate getDataLotacao() {
        return dataLotacao;
    }

    public void setDataLotacao(LocalDate dataLotacao) {
        this.dataLotacao = dataLotacao;
    }

    public LocalDate getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(LocalDate dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }
}
