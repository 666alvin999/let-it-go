package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.CreateAlbumForm;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

@Component
public class CreateAlbumFormMapper {

	public Album mapToAlbum(CreateAlbumForm createAlbumForm) {
		return new Album(
			new AlbumName(createAlbumForm.getAlbumName()),
			new Username(createAlbumForm.getUsername())
		);
	}

}
