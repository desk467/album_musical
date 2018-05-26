package exceptions;

import com.desk467.album.entidades.Artista;

public class ArtistaNaoEncontradoException extends Exception {

    public ArtistaNaoEncontradoException(Long id) {
        super(String.format("O artista id=%d requisitado não foi encontrado.", id));
    }

    public ArtistaNaoEncontradoException(Artista artista) {
        super(String.format("O artista %s requisitado não foi encontrado.", artista.toString()));
    }
}
