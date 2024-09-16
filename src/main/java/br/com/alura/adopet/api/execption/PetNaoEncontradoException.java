package br.com.alura.adopet.api.execption;

public class PetNaoEncontradoException extends RuntimeException {

    public PetNaoEncontradoException(String message) {
        super(message);
    }
}
