package com.paulomarchon.testepratico.cidade;

import com.paulomarchon.testepratico.cidade.dto.CidadeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cidade")
public class CidadeController {
    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<CidadeDto> buscarTodasCidades() {
        return  cidadeService.buscarTodasCidades();
    }

    @GetMapping("nome={nomeCidade}")
    public List<CidadeDto> buscarCidadePorNome(@PathVariable("nomeCidade") String nomeCidade) {
        return cidadeService.buscarCidadePorNome(nomeCidade);
    }

    @GetMapping("uf={siglaUf}")
    public List<CidadeDto> buscarCidadePorUf(@PathVariable("siglaUf") String siglaUf) {
        return cidadeService.buscarCidadePorUf(siglaUf);
    }

    @GetMapping("enderecos={nomeCidade}")
    public List<String> buscarEnderecosDaCidade(@PathVariable("nomeCidade")String nomeCidade) {
        return cidadeService.buscarEnderecosDeCidade(nomeCidade);
    }
}
