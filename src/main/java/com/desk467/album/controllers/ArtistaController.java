package com.desk467.album.controllers;

import com.desk467.album.entidades.Artista;
import com.desk467.album.servicos.ArtistaService;
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
@RequestMapping("/api/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService service;

    @GetMapping
    public ResponseEntity<?> listaArtistas(){
        List<Artista> artistas = service.recuperarTodos();

        if (artistas.isEmpty() ) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(artistas);
        }
    }

    @PostMapping
    public ResponseEntity<Artista> cria(@Validated @RequestBody Artista artista, HttpServletResponse response) {

        Artista artistaSalvo = service.salvar(artista);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(artistaSalvo.getId())
                .toUri();

        return  ResponseEntity.created(uri).body(artistaSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> buscaPor(@PathVariable Long id ) {
        try {
            return ResponseEntity.ok(service.recuperar(id));
        } catch(ArtistaNaoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void excluir(@PathVariable Long id) {
        service.excluir(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Validated @RequestBody Artista artista) {

        Artista manager = null;
        try {
            manager = service.atualizar(id, artista);
            return ResponseEntity.ok(manager);

        } catch (ArtistaNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
