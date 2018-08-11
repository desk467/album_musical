package com.desk467.album.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Entity
public class Artista {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message = "Campo 'nome' é obrigatório.")
    @Size(min = 3, max = 50)
    private String nome;

    @Column
    @NotBlank(message = "Campo 'nacionalidade' é obrigatório.")
    @Size(min = 3, max = 50, message = "Campo 'nacionalidade' deve possuir entre 3 e 50 caracteres")
    private String nacionalidade;


    @OneToMany
    private Collection<Album> albuns = new ArrayList<>();

    public Artista() {}

    public Artista(Long id) {
        this.id = id;
    }

    public Artista(Long id, String nome, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
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

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Collection<Album> getAlbuns() {
        return albuns;
    }

    public void setAlbuns(Collection<Album> albuns) {
        this.albuns = albuns;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return Objects.equals(id, artista.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
