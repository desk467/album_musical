package com.desk467.album.repositorios;

import com.desk467.album.entidades.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository  extends JpaRepository<Album, Long> {
}
