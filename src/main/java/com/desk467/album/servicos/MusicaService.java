package com.desk467.album.servicos;

import com.desk467.album.entidades.Album;
import com.desk467.album.entidades.Musica;
import com.desk467.album.repositorios.AlbumRepository;
import com.desk467.album.repositorios.MusicaRepository;
import exceptions.AlbumNaoEncontradoException;
import exceptions.MusicaNaoEncontradaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MusicaService {
    private final MusicaRepository repo;
    private final GenericService<Musica> genericService;

    @Autowired
    public MusicaService(MusicaRepository repo) {
        this.repo = repo;
        genericService = new GenericService<>(this.repo);
    }

    public List<Musica> recuperarTodos(){
        return genericService.recuperarTodos();
    }

    @Transactional(readOnly = true)
    public Optional<Musica> recuperar(Long id) {
        return genericService.recuperar(id);
    }

    @Transactional
    public Musica salvar(Musica musica) {
        return genericService.salvar(musica);
    }

    public Optional<Musica> atualizar(Long id, Musica musica){
        return genericService.atualizar(id, musica);
    }

    public Page<Musica> buscaPaginada(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional
    public void excluir(Long id) {
        genericService.excluir(id);
    }

}
