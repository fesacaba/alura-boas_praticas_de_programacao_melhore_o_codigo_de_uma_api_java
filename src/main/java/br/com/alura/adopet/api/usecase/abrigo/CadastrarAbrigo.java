package br.com.alura.adopet.api.usecase.abrigo;

import br.com.alura.adopet.api.controller.dto.AbrigoDto;
import br.com.alura.adopet.api.execption.AbrigoCadastradoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarAbrigo {

    private final AbrigoRepository abrigoRepository;

    public void execute(@Valid AbrigoDto abrigoDto) {

        boolean nomeJaCadastrado = abrigoRepository.existsByNome(abrigoDto.nome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(abrigoDto.telefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(abrigoDto.email());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new AbrigoCadastradoException("Dados já cadastrados para outro abrigo!");
        } else {
            abrigoRepository.save(
                    Abrigo
                            .builder()
                            .email(abrigoDto.email())
                            .nome(abrigoDto.nome())
                            .telefone(abrigoDto.telefone())
                            .build()
            );
        }
    }
}
