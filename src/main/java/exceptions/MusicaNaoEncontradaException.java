package exceptions;

import com.desk467.album.entidades.Artista;

public class MusicaNaoEncontradaException extends Exception {

    public MusicaNaoEncontradaException(Long id) {
        super(String.format("A música id=%d requisitada não foi encontrada.", id));
    }

    public MusicaNaoEncontradaException(Artista artista) {
        super(String.format("A música %s requisitada não foi encontrada.", artista.toString()));
    }
}
