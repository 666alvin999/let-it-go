package org.letitgo.infrastructure.adapters;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;
import org.letitgo.infrastructure.daos.AlbumDao;
import org.letitgo.infrastructure.dtos.AlbumDTO;
import org.letitgo.infrastructure.mappers.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlbumAdapter implements AlbumPort {

	private AlbumDao albumDao;
	private AlbumMapper albumMapper;

	@Autowired
	public AlbumAdapter(AlbumDao albumDao, AlbumMapper albumMapper) {
		this.albumDao = albumDao;
		this.albumMapper = albumMapper;
	}

	public ActionSuccess save(Album album) {
		AlbumDTO albumDTO = this.albumMapper.mapToDTO(album);

		return this.albumDao.save(albumDTO);
	}

}
