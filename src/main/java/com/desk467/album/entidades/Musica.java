package com.desk467.album.entidades;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Musica {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message="Campo 'nome' é obrigatório.")
    @Size(min = 3, max = 50)
    private String nome;

    @Column
    @NotNull(message="Campo 'duracao' é obrigatório.")
    @Positive(message = "É necessário informar um valor maior que zero para a duração da música")
    private Integer duracao;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @OneToMany
    private Collection<Artista> autores = new ArrayList<>();

    public Musica() {}

    public Musica(String nome, Integer duracao) {
        this.nome = nome;
        this.duracao = duracao;
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

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Collection<Artista> getAutores() {
        return autores;
    }

    public void setAutores(Collection<Artista> autores) {
        this.autores = autores;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musica musica = (Musica) o;
        return Objects.equals(getId(), musica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
