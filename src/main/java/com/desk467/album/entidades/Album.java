package com.desk467.album.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message = "Campo 'nome' obrigatório.")
    @Size(min = 3, max = 50, message = "Campo 'nome' deve possuir entre 3 e 50 caracteres.")
    private String nome;

    @Column
    @NotNull(message = "Campo 'ano' obrigatório.")
    @Positive(message = "É necessário informar um valor maior que zero para o campo 'ano'.")
    private Integer ano;


    @ManyToMany
    @JoinTable(name = "artista_album",
            joinColumns = @JoinColumn(name = "artista_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Collection<Artista> artistas = new ArrayList<>();


    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Collection<Musica> musicas = new ArrayList<>();

    public Album() {}

    public Album(Long id) {
        this.id = id;
    }

    public Album(String nome, Integer ano) {
        this.nome = nome;
        this.ano = ano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Collection<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(Collection<Musica> musicas) {
        this.musicas = musicas;
    }


    public Collection<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(Collection<Artista> artistas) {
        this.artistas = artistas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
