package br.com.alura.adopet.api.usecase.adocao.validacoes;

import br.com.alura.adopet.api.controller.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.execption.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ValidacaoPetDisponivel implements Validacao{

    private final PetRepository petRepository;

    @Override
    public void run(SolicitacaoAdocaoDTO dto) {

        Pet pet = petRepository.getReferenceById(dto.idPet());

        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
