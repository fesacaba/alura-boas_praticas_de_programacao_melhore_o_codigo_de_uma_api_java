package br.com.alura.adopet.api.usecase.adocao.validacoes;

import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;

public interface Validacao {

    void run(SolicitacaoAdocaoDTO dto);
}
