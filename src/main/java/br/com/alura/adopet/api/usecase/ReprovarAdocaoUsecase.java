package br.com.alura.adopet.api.usecase;

import br.com.alura.adopet.api.controller.dto.ReprovacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.EmailRequest;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReprovarAdocaoUsecase {

    private final AdocaoRepository repository;
    private final SendEmailUsecase sendEmailUsecase;
    private final AdocaoRepository adocaoRepository;

    public void execute(ReprovacaoAdocaoDTO dto) {
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.setStatus(StatusAdocao.REPROVADO);
        repository.save(Adocao.builder().build());

        sendEmailUsecase.execute(EmailRequest.builder()
                .to(adocao.getTutor().getEmail())
                .from("adopet@email.com.br")
                .subject("Adoção reprovada")
                .msg("Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus())
                .build());
    }
}
