package com.paulomarchon.testepratico.unidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade,Integer> {

    Optional<Unidade> findByNome(String nome);
    Page<Unidade> findBySigla(String sigla, Pageable pageable);
    boolean existsUnidadeByNome(String nome);
}
