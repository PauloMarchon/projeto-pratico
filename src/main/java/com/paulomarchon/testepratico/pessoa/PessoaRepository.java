package com.paulomarchon.testepratico.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Optional<Pessoa> findByNome(String nome);

    boolean existsById(Integer id);
}
