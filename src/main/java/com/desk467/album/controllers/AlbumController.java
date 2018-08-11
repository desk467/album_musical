package com.desk467.album.controllers;

import com.desk467.album.entidades.Album;
import com.desk467.album.entidades.Artista;
import com.desk467.album.servicos.AlbumService;
import com.desk467.album.servicos.ArtistaService;
import exceptions.AlbumNaoEncontradoException;
import exceptions.ArtistaNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/albuns")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @GetMapping
    public ResponseEntity<?> listaAlbuns(){
        List<Album> albuns = service.recuperarTodos();

        if (albuns.isEmpty() ) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(albuns);
        }
    }

    @PostMapping
    public ResponseEntity<Album> cria(@Validated @RequestBody Album album, HttpServletResponse response) {

        Album albumSalvo = service.salvar(album);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(albumSalvo.getId())
                .toUri();

        return  ResponseEntity.created(uri).body(albumSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> buscaPor(@PathVariable Long id ) {
        try {
            return ResponseEntity.ok(
                    service.recuperar(id)
                            .orElseThrow(() -> new AlbumNaoEncontradoException(id))
            );

        } catch(AlbumNaoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void excluir(@PathVariable Long id) {
        service.excluir(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Album> atualizar(@PathVariable Long id, @Validated @RequestBody Album album) {

        Album manager = null;
        try {
            manager = service.atualizar(id, album).orElseThrow(() -> new AlbumNaoEncontradoException(id));
            return ResponseEntity.ok(manager);

        } catch (AlbumNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
