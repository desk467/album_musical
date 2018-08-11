package exceptions;

import com.desk467.album.entidades.Album;

public class AlbumNaoEncontradoException extends Exception {
    public AlbumNaoEncontradoException(Long id) {
        super(String.format("O album id=%d requisitado não foi encontrado.", id));
    }

    public AlbumNaoEncontradoException(Album album) {
        super(String.format("O album %s requisitado não foi encontrado.", album.toString()));
    }
}
