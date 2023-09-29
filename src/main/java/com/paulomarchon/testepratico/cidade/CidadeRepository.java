package com.paulomarchon.testepratico.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Page<Cidade> findByNome(String nome, Pageable pageable);
    Page<Cidade> findByUf(String uf, Pageable pageable);
}
