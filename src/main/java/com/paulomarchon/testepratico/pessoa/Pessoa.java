package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.endereco.Endereco;
import com.paulomarchon.testepratico.foto.FotoPessoa;
import com.paulomarchon.testepratico.lotacao.Lotacao;
import com.paulomarchon.testepratico.servidor.Servidor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pessoa", schema = "pessoas")
public class Pessoa {
    @Id
    @SequenceGenerator(
            name = "pessoa_id_seq",
            sequenceName = "pessoa_id_seq",
            schema = "pessoas",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pessoa_id_seq"
    )
    @Column(name = "pes_id")
    private Integer id;

    @Column(name = "pes_nome")
    private String nome;

    @Column(name = "pes_data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "pes_sexo")
    private String sexo;

    @Column(name = "pes_mae")
    private String nomeMae;

    @Column(name = "pes_pai")
    private String nomePai;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Servidor servidor;

    @OneToOne
    @JoinTable(name = "pessoa_endereco", schema = "pessoas",
        joinColumns =
                { @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")},
            inverseJoinColumns =
                { @JoinColumn(name = "end_id", referencedColumnName = "end_id")}
    )
    private Endereco endereco;

    @OneToMany
    private Collection<FotoPessoa> foto;

    @OneToOne(mappedBy = "pessoa")
    private Lotacao lotacao;

    public Pessoa() {
    }

    public Pessoa(Integer id, String nome, LocalDate dataNascimento, String sexo, String nomeMae, String nomePai) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.nomeMae = nomeMae;
        this.nomePai = nomePai;
    }

    public Pessoa(String nome, LocalDate dataNascimento, String sexo, String nomeMae, String nomePai) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.nomeMae = nomeMae;
        this.nomePai = nomePai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Collection<FotoPessoa> getFoto() {
        return foto;
    }

    public void setFoto(Collection<FotoPessoa> foto) {
        this.foto = foto;
    }

    public Lotacao getLotacao() {
        return lotacao;
    }

    public void setLotacao(Lotacao lotacao) {
        this.lotacao = lotacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id) && Objects.equals(nome, pessoa.nome) && Objects.equals(dataNascimento, pessoa.dataNascimento) && Objects.equals(sexo, pessoa.sexo) && Objects.equals(nomeMae, pessoa.nomeMae) && Objects.equals(nomePai, pessoa.nomePai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataNascimento, sexo, nomeMae, nomePai);
    }
}
