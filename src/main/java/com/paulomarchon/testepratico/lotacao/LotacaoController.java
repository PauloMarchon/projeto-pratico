package com.paulomarchon.testepratico.lotacao;

import com.paulomarchon.testepratico.lotacao.dto.LotacaoCadastro;
import com.paulomarchon.testepratico.lotacao.dto.LotacaoDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lotacao")
public class LotacaoController {
    private final LotacaoService lotacaoService;

    public LotacaoController(LotacaoService lotacaoService) {
        this.lotacaoService = lotacaoService;
    }

    @GetMapping
    public List<LotacaoDto> buscarLotacoes() {
        return lotacaoService.buscarTodasLotacoes();
    }

    @GetMapping("{lotacaoId}")
    public LotacaoDto buscarLotacao(@PathVariable("lotacaoId")Integer lotacaoId) {
        return lotacaoService.buscarLotacaoPorId(lotacaoId);
    }

    @GetMapping ("{lotacaoPortaria}")
    LotacaoDto buscarLotacao(@PathVariable("lotacaoPortaria") String portaria) {
        return lotacaoService.buscarLotacaoPorPortaria(portaria);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarLotacao(@Valid @RequestBody LotacaoCadastro lotacaoCadastro) {
        lotacaoService.cadastrarLotacao(lotacaoCadastro);

        return ResponseEntity.ok()
                .body(HttpStatus.CREATED);
    }

    @DeleteMapping("{lotacaoId}")
    public void excluirLotacao(@PathVariable("lotacaoId")Integer lotacaoId) {
        lotacaoService.excluirLotacao(lotacaoId);
    }

    @PutMapping("{lotacaoId}")
    public void atualizarLotacao(
            @PathVariable("lotacaoId") Integer lotacaoId,
            @RequestBody LotacaoDto lotacaoAtualizada
    ) {
        lotacaoService.atualizarLotacao(lotacaoId, lotacaoAtualizada);
    }
}
