package com.paulomarchon.testepratico.endereco;

import com.paulomarchon.testepratico.endereco.dto.EnderecoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public List<EnderecoDto> buscarTodosEnderecos() {
        return enderecoService.buscarTodosEnderecos();
    }
    @GetMapping("/logradouro={nomeLogradouro}")
    public List<EnderecoDto> buscarEnderecosPorLogradouro(@PathVariable("nomeLogradouro") String logradouro) {
        return enderecoService.buscarEnderecosPorLogradouro(logradouro);
    }
    @GetMapping("/bairro={nomeBairro}")
    public List<EnderecoDto> buscarEnderecoPorBairro(@PathVariable("nomeBairro")String bairro) {
        return enderecoService.buscarEnderecoPorBairro(bairro);
    }
}
