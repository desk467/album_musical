package com.desk467.album.servicos;

import com.desk467.album.entidades.Artista;
import com.desk467.album.repositorios.ArtistaRepository;
import exceptions.ArtistaNaoEncontradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistaService {

    private ArtistaRepository repo;

    @Autowired
    public ArtistaService(ArtistaRepository repo) {
        this.repo = repo;
    }

    public List<Artista> recuperarTodos(){
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Artista recuperar(Long id) throws ArtistaNaoEncontradoException {
        return repo.findById(id).orElseThrow(() -> new ArtistaNaoEncontradoException(id));
    }

    @Transactional
    public Artista salvar(Artista artista) {
        return repo.save(artista);
    }

    public Artista atualizar(Long id, Artista novoArtista) throws ArtistaNaoEncontradoException {
        Artista manager = recuperar(id);

        BeanUtils.copyProperties(novoArtista, manager, "id");
        salvar(manager);

        return manager;
    }

    @Transactional
    public void excluir(Long id) {
        repo.deleteById(id);
    }

}
