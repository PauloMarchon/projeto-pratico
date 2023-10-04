package com.paulomarchon.testepratico.unidade;

import com.paulomarchon.testepratico.unidade.dto.UnidadeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/unidade")
public class UnidadeController {
    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping
    public List<UnidadeDto> listarTodasUnidades() {
        return unidadeService.buscarTodasUnidades();
    }

    @GetMapping("/busca={nomeUnidade}")
    public UnidadeDto buscarUnidadePorNome(@PathVariable("nomeUnidade") String nomeUnidade) {
        return unidadeService.buscarUnidadePorNome(nomeUnidade);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUnidade(@RequestBody UnidadeDto unidadeCadastro) {
        unidadeService.cadastrarUnidade(unidadeCadastro);
        return ResponseEntity.ok()
                .body(HttpStatus.CREATED);
    }

    @DeleteMapping("{unidadeId}")
    public void excluirUnidade(@PathVariable("unidadeId") Integer unidadeId) {
        unidadeService.excluirUnidade(unidadeId);
    }

    @PutMapping("{unidadeId}")
    public void atualizarUnidade(
            @PathVariable("unidadeId") Integer unidadeId,
            @RequestBody UnidadeDto unidadeAtualizada
    ) {
        unidadeService.atualizarUnidade(unidadeId, unidadeAtualizada);
    }
}
