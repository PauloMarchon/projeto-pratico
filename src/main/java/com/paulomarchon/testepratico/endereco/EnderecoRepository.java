package com.paulomarchon.testepratico.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Page<Endereco> findByLogradouro(String logradouro, Pageable pageable);
    Page<Endereco> findByBairro(String bairro, Pageable pageable);
}
