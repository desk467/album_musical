package com.desk467.album.repositorios;

import com.desk467.album.entidades.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository  extends JpaRepository<Musica, Long> {
    Musica findByNome(String nome);
    Musica findByNomeAlbum(String nome);
    Musica findByNomeArtista(String nome);
}
