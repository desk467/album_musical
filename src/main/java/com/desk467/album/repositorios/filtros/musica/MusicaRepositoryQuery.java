package com.desk467.album.repositorios.filtros.musica;

import com.desk467.album.entidades.Musica;
import com.desk467.album.repositorios.filtros.MusicaFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MusicaRepositoryQuery {
    List<Musica> filtrar(MusicaFiltro filtro);

    Page<Musica> filtrar(MusicaFiltro filtro, Pageable pageable);

}

