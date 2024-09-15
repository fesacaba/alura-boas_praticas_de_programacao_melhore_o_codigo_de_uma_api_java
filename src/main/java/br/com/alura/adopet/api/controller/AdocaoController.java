package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.controller.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.ReprovacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.execption.ValidacaoException;
import br.com.alura.adopet.api.usecase.AprovarAdocaoUsecase;
import br.com.alura.adopet.api.usecase.ReprovarAdocaoUsecase;
import br.com.alura.adopet.api.usecase.SolicitarAdocaoUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/adocoes")
@RequiredArgsConstructor
public class AdocaoController {

    private final SolicitarAdocaoUsecase solicitarAdocaoUsecase;
    private final AprovarAdocaoUsecase aprovarAdocaoUsecase;
    private final ReprovarAdocaoUsecase reprovarAdocaoUsecase;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDTO adocao) {
        try {
            solicitarAdocaoUsecase.execute(adocao);
            return ResponseEntity.ok("Adoção solicitada com Sucesso");
        } catch (ValidacaoException validacaoException) {
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDTO adocao) {
        aprovarAdocaoUsecase.execute(adocao);
        return ResponseEntity.ok("Aprovação realizada com Sucesso");
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDTO adocao) {
        reprovarAdocaoUsecase.execute(adocao);
        return ResponseEntity.ok("Reprovação realizada com Sucesso");
    }
}
