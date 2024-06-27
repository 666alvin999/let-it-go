package org.letitgo.application.mappers.out;

import org.letitgo.application.dtos.out.AlbumCountViewModel;
import org.springframework.stereotype.Component;

@Component
public class AlbumCountViewModelMapper {

	public AlbumCountViewModel mapToViewModel(int albumCount) {
		return new AlbumCountViewModel(albumCount);
	}

}
