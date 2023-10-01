package com.paulomarchon.testepratico.endereco;

import com.paulomarchon.testepratico.endereco.dto.EnderecoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoDtoMapper enderecoDtoMapper;

    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoDtoMapper enderecoDtoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoDtoMapper = enderecoDtoMapper;
    }

    public List<EnderecoDto> buscarTodosEnderecos() {
        Page<Endereco> page = enderecoRepository.findAll(Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(enderecoDtoMapper)
                .collect(Collectors.toList());
    }

    public List<EnderecoDto> buscarEnderecosPorLogradouro(String logradouro) {
        Page<Endereco> page = enderecoRepository.findByLogradouro(logradouro.toUpperCase(), Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(enderecoDtoMapper)
                .collect(Collectors.toList());
    }
    public List<EnderecoDto> buscarEnderecoPorBairro(String bairro) {
        Page<Endereco> page = enderecoRepository.findByBairro(bairro.toUpperCase(), Pageable.ofSize(20));
        return page.getContent()
                .stream()
                .map(enderecoDtoMapper)
                .collect(Collectors.toList());
    }
}
