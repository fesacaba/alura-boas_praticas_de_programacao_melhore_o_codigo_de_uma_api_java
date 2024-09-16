package br.com.alura.adopet.api.usecase.pet;

import br.com.alura.adopet.api.controller.dto.PetDto;
import br.com.alura.adopet.api.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListarPetDisponivel {

    private final PetRepository repository;

    public List<PetDto> execute() {
        return repository.findAllByAdotadoFalse().stream().map(PetDto::new).toList();
    }
}
