package br.com.alura.adopet.api.usecase.validacoes;

import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;

public interface Validacao {

    void run(SolicitacaoAdocaoDTO dto);
}
