package br.com.alura.adopet.api.usecase.adocao;

import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.EmailRequest;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.usecase.adocao.validacoes.Validacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitarAdocaoUsecase {

    private final AdocaoRepository adocaoRepository;
    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;
    private final SendEmailUsecase sendEmailUsecase;
    private final List<Validacao> validacoes;

    public void execute(SolicitacaoAdocaoDTO dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        //strategy (S do SOLID) + chain of responsibility (O do SOLID)
        validacoes.forEach(v -> v.run(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());

        adocaoRepository.save(adocao);

        sendEmailUsecase.execute(EmailRequest.builder()
                .to(adocao.getTutor().getEmail())
                .from("adopet@email.com.br")
                .subject("Solicitação de adoção")
                .msg("Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.")
                .build());
    }

}
