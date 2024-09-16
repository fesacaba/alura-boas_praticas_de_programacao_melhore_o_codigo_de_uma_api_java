package br.com.alura.adopet.api.usecase.pet;

import br.com.alura.adopet.api.model.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListarPetPorNomeOuEmail {


    private final BuscaPetPorId buscaPetPorId;
    private final BuscaPetPorNome buscaPetPorNome;

    public List<Pet> execute(String param) {
        try {
            return buscaPetPorId.execute(Long.parseLong(param));
        } catch (NumberFormatException e) {
            return buscaPetPorNome.execute(param);
        }
    }
}
