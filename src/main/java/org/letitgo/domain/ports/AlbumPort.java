package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;

public interface AlbumPort {

	ActionSuccess save(Album album);

}
