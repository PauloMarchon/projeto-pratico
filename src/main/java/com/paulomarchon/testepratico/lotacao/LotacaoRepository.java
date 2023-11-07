package com.paulomarchon.testepratico.lotacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotacaoRepository extends JpaRepository<Lotacao, Integer> {
    Optional<Lotacao> findByPortaria(String portaria);
    boolean existsById(Integer id);
    boolean existsByPortaria(String portaria);
}
