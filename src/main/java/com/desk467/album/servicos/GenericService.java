package com.desk467.album.servicos;

import com.desk467.album.entidades.Album;
import com.desk467.album.entidades.Artista;
import com.desk467.album.repositorios.AlbumRepository;
import com.desk467.album.repositorios.ArtistaRepository;
import exceptions.AlbumNaoEncontradoException;
import exceptions.ArtistaNaoEncontradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class GenericService<T> {

    private JpaRepository repo;
    private Exception itemNaoEncontrado = new Exception("Item não encontrado.");

    public GenericService(JpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    public GenericService(JpaRepository<T, Long> repo, Exception itemNaoEncontrado) {
        this.repo = repo;
        this.itemNaoEncontrado = itemNaoEncontrado;
    }


    public List<T> recuperarTodos(){
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<T> recuperar(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public T salvar(T item) {
        return (T) repo.save(item);
    }

    public Optional<T> atualizar(Long id, T novoItem) {
        Optional<T> manager = recuperar(id);

        BeanUtils.copyProperties(novoItem, manager, "id");
        salvar(manager.get());

        return manager;
    }

    @Transactional
    public void excluir(Long id) {
        repo.deleteById(id);
    }

}
