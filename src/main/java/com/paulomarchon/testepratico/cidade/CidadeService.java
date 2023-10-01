package com.paulomarchon.testepratico.cidade;

import com.paulomarchon.testepratico.cidade.dto.CidadeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CidadeService {
    private final CidadeRepository cidadeRepository;
    private final CidadeDtoMapper cidadeDtoMapper;

    public CidadeService(CidadeRepository cidadeRepository, CidadeDtoMapper cidadeDtoMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeDtoMapper = cidadeDtoMapper;
    }

    public List<CidadeDto> buscarTodasCidades() {
        Page<Cidade> page = cidadeRepository.findAll(Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(cidadeDtoMapper)
                .collect(Collectors.toList());
    }
    public List<CidadeDto> buscarCidadePorNome(String nome) {
        Page<Cidade> page = cidadeRepository.findByNome(nome.toUpperCase(), Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(cidadeDtoMapper)
                .collect(Collectors.toList());
    }

    public List<CidadeDto> buscarCidadePorUf(String uf) {
        Page<Cidade> page = cidadeRepository.findByUf(uf.toUpperCase(), Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(cidadeDtoMapper)
                .collect(Collectors.toList());
    }

    public List<String> buscarEnderecosDeCidade(String nomeCidade) {
        Cidade cidade;
        Optional<Cidade> buscaCidade = cidadeRepository.findByNome(nomeCidade.toUpperCase());

        if (buscaCidade.isPresent()) {
            cidade = buscaCidade.get();
        } else {
            throw new RuntimeException("");
        }
        return Collections.singletonList(cidade.getEnderecos().toString());
    }
}
