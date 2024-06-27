package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;

import java.util.List;

public interface AlbumPort {

	ActionSuccess save(Album album);

	ActionSuccess delete(Album album);

	List<Album> getAlbumsByUsername(String username);

	int getAlbumCountByUsername(String username);

}
