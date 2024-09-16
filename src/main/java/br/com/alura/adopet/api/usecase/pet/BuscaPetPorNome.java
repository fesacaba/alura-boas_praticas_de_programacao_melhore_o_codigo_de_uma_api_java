package br.com.alura.adopet.api.usecase.pet;

import br.com.alura.adopet.api.execption.PetNaoEncontradoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscaPetPorNome {

    private final AbrigoRepository abrigoRepository;

    public List<Pet> execute(String param) {
        try {
            return abrigoRepository.findByNome(param).getPets();
        } catch (EntityNotFoundException enfe) {
            throw new PetNaoEncontradoException("Pet não encontrado");
        }
    }
}
