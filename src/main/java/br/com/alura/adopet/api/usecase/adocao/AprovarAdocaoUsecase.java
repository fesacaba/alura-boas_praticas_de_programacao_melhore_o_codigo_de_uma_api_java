package br.com.alura.adopet.api.usecase.adocao;

import br.com.alura.adopet.api.controller.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.EmailRequest;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AprovarAdocaoUsecase {

    private final AdocaoRepository repository;
    private final AdocaoRepository adocaoRepository;
    private final SendEmailUsecase sendEmailUsecase;

    public void execute(AprovacaoAdocaoDTO dto) {

        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoAprovado();
        repository.save(adocao);

        sendEmailUsecase.execute(EmailRequest.builder()
                .to(adocao.getTutor().getEmail())
                .from("adopet@email.com.br")
                .subject("Adoção aprovada")
                .msg("Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.")
                .build());
    }
}
