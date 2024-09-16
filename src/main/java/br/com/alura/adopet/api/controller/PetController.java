package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.controller.dto.PetDto;
import br.com.alura.adopet.api.usecase.pet.ListarPetDisponivel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final ListarPetDisponivel listarPetDisponivel;

    @GetMapping
    public ResponseEntity<List<PetDto>> listarTodosDisponiveis() {
        return ResponseEntity.ok(listarPetDisponivel.execute());
    }
}
