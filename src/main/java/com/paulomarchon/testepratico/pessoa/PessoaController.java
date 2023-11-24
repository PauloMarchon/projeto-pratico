package com.paulomarchon.testepratico.pessoa;

import com.paulomarchon.testepratico.pessoa.dto.PessoaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<PessoaDto> buscarTodasPessoas() {
        return pessoaService.buscarTodasPessoas();
    }

    @GetMapping("{nomePessoa}")
    public PessoaDto buscarPessoaPorNome(@PathVariable("nomePessoa")String nomePessoa) {
        return pessoaService.buscarPessoaPorNome(nomePessoa);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaDto cadastroPessoa) {
        pessoaService.cadastrarPessoa(cadastroPessoa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{pessoaId}")
    public void atualizarPessoa(@PathVariable("pessoaId") Integer pessoaId ,
                                @RequestBody PessoaDto pessoaAtualizada
    ) {
        pessoaService.atualizarPessoa(pessoaId, pessoaAtualizada);
    }

    @DeleteMapping("{pessoaId}")
    public void excluirPessoa(@PathVariable("pessoaId") Integer pessoaId) {
        pessoaService.deletarPessoa(pessoaId);
    }

}
