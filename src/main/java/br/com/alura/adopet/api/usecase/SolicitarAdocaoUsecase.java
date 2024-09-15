package br.com.alura.adopet.api.usecase;

import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.controller.dto.EmailRequest;
import br.com.alura.adopet.api.execption.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitarAdocaoUsecase {

    private final AdocaoRepository adocaoRepository;
    private final TutorRepository tutorRepository;
    private final PetRepository petRepository;
    private final SendEmailUsecase sendEmailUsecase;

    public void execute(SolicitacaoAdocaoDTO dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        if (pet.getAdotado() == true) {
            throw new ValidacaoException("Pet já foi adotado!");
        } else {
            List<Adocao> adocoes = adocaoRepository.findAll();
            for (Adocao a : adocoes) {
                if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
                }
            }
            for (Adocao a : adocoes) {
                if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
                }
            }
            for (Adocao a : adocoes) {
                int contador = 0;
                if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                    contador = contador + 1;
                }
                if (contador == 5) {
                    throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
                }
            }
        }

        Adocao adocao = Adocao
                .builder()
                .data(LocalDateTime.now())
                .status(StatusAdocao.AGUARDANDO_AVALIACAO)
                .pet(pet)
                .tutor(tutor)
                .motivo(dto.motivo())
                .build();

        adocaoRepository.save(adocao);

        sendEmailUsecase.execute(EmailRequest.builder()
                .to(adocao.getTutor().getEmail())
                .from("adopet@email.com.br")
                .subject("Solicitação de adoção")
                .msg("Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.")
                .build());
    }

}
