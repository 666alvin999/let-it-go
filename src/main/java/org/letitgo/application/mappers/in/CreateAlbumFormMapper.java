package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.AlbumForm;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

@Component
public class CreateAlbumFormMapper {

	public Album mapToAlbum(AlbumForm albumForm) {
		return new Album(
			new AlbumName(albumForm.getAlbumName()),
			new Username(albumForm.getUsername())
		);
	}

}
