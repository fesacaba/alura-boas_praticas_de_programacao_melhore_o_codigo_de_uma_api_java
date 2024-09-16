package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.controller.dto.AbrigoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.usecase.abrigo.CadastrarAbrigo;
import br.com.alura.adopet.api.usecase.pet.ListarPetPorNomeOuEmail;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abrigos")
public class AbrigoController {

    private final AbrigoRepository repository;
    private final CadastrarAbrigo cadastrarAbrigo;
    private final ListarPetPorNomeOuEmail listarPetPorNomeOuEmail;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid AbrigoDto abrigoDto) {
        cadastrarAbrigo.execute(abrigoDto);
        ResponseEntity.ok();
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<Pet>> listarPets(@PathVariable String idOuNome) {
       return ResponseEntity.ok(listarPetPorNomeOuEmail.execute(idOuNome));
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            repository.save(abrigo);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                repository.save(abrigo);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
