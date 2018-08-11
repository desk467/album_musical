package com.desk467.album.repositorios.filtros.musica;

import com.desk467.album.entidades.Artista;
import com.desk467.album.entidades.Musica;
import com.desk467.album.repositorios.filtros.MusicaFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MusicaRepositoryImpl implements MusicaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Musica> filtrar(MusicaFiltro filtro) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Musica> cq = cb.createQuery(Musica.class);

        Root<Musica> musicaRoot = cq.from(Musica.class);

        Predicate[] restricoes = criaRestricoes(filtro, cb, musicaRoot);

        cq.select(musicaRoot)
                .where(restricoes)
                .orderBy(cb.asc(musicaRoot.get("nome")));

        return manager.createQuery(cq)
                .getResultList();
    }

    private Predicate[] criaRestricoes(MusicaFiltro filtro, CriteriaBuilder cb, Root<Musica> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filtro.getNomeMusica())) {

            predicates.add(cb.like(root.get("nome"),
                    "%" + filtro.getNomeMusica() + "%"));
        }

        if (!StringUtils.isEmpty(filtro.getNomeAlbum())) {
            predicates.add(cb.like(root.get("album"),
                    "%" + filtro.getNomeAlbum() + "%"));
        }

        if (!StringUtils.isEmpty(filtro.getNomeArtista())) {
            predicates.add(cb.like(root.get("artista"),
                    "%" + filtro.getNomeArtista() + "%"));
        }

        return predicates.toArray(new Predicate[ predicates.size() ] );
    }

    @Override
    public Page<Musica> filtrar(MusicaFiltro filtro, Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Musica> cq = cb.createQuery(Musica.class );

        Root<Musica> root = cq.from(Musica.class);

        Predicate[] restricoes = this.criaRestricoes(filtro, cb, root);

        TypedQuery<Musica> query = manager.createQuery(cq);
        adicionaRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, getTotal(filtro) );
    }

    private void adicionaRestricoesDePaginacao(TypedQuery<Musica> query, Pageable pageable) {
        Integer paginaAtual = pageable.getPageNumber();

        Integer totalObjetosPorPagina = pageable.getPageSize();
        Integer primeiroObjetoDaPagina = paginaAtual * totalObjetosPorPagina;

        query.setFirstResult(primeiroObjetoDaPagina);
        query.setMaxResults(totalObjetosPorPagina);
    }

    private Long getTotal(MusicaFiltro filtro) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Musica> root = cq.from(Musica.class);

        Predicate[] predicates = criaRestricoes(filtro, cb, root);
        cq.where(predicates);

        cq.select(cb.count(root));

        return manager.createQuery(cq).getSingleResult();
    }
}
