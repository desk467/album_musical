package com.desk467.album.controllers;

import com.desk467.album.entidades.Album;
import com.desk467.album.entidades.Musica;
import com.desk467.album.servicos.AlbumService;
import com.desk467.album.servicos.MusicaService;
import exceptions.AlbumNaoEncontradoException;
import exceptions.MusicaNaoEncontradaException;
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
@RequestMapping("/api/musicas")
public class MusicaController {

    @Autowired
    private MusicaService service;

    @GetMapping
    public ResponseEntity<?> listaMusicas(){
        List<Musica> musicas = service.recuperarTodos();

        if (musicas.isEmpty() ) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(musicas);
        }
    }

    @PostMapping
    public ResponseEntity<Musica> cria(@Validated @RequestBody Musica musica, HttpServletResponse response) {

        Musica musicaSalva = service.salvar(musica);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(musicaSalva.getId())
                .toUri();

        return  ResponseEntity.created(uri).body(musicaSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> buscaPor(@PathVariable Long id ) {
        try {
            return ResponseEntity.ok(
                    service.recuperar(id)
                            .orElseThrow(() -> new MusicaNaoEncontradaException(id))
            );

        } catch(MusicaNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void excluir(@PathVariable Long id) {
        service.excluir(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Validated @RequestBody Musica musica) {

        Musica manager = null;
        try {
            manager = service.atualizar(id, musica).orElseThrow(() -> new MusicaNaoEncontradaException(id));
            return ResponseEntity.ok(manager);

        } catch (MusicaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
