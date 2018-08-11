package com.desk467.album.servicos;

import com.desk467.album.entidades.Album;
import com.desk467.album.entidades.Artista;
import com.desk467.album.repositorios.AlbumRepository;
import com.desk467.album.repositorios.ArtistaRepository;
import exceptions.AlbumNaoEncontradoException;
import exceptions.ArtistaNaoEncontradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final GenericService<Album> genericService;

    @Autowired
    public AlbumService(AlbumRepository repo) {
        genericService = new GenericService<Album>(repo);
    }

    public List<Album> recuperarTodos(){
        return genericService.recuperarTodos();
    }

    @Transactional(readOnly = true)
    public Optional<Album> recuperar(Long id) {
        return genericService.recuperar(id);
    }

    @Transactional
    public Album salvar(Album album) {
        return genericService.salvar(album);
    }

    public Optional<Album> atualizar(Long id, Album novoAlbum){
        return genericService.atualizar(id, novoAlbum);
    }

    @Transactional
    public void excluir(Long id) {
        genericService.excluir(id);
    }

}
