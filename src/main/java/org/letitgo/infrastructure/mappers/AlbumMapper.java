package org.letitgo.infrastructure.mappers;

import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.AlbumDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumMapper {

	public AlbumDTO mapToDTO(Album album) {
		return new AlbumDTO(
			album.albumName().value(),
			album.username().value()
		);
	}

	public Album mapToAlbum(AlbumDTO albumDTO) {
		return new Album(
			new AlbumName(albumDTO.getAlbumName()),
			new Username(albumDTO.getUsername())
		);
	}

	public List<Album> mapAllToAlbums(List<AlbumDTO> albumDTOs) {
		return albumDTOs.stream().map(this::mapToAlbum).toList();
	}

}
