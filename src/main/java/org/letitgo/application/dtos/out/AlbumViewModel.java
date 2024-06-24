package org.letitgo.application.dtos.out;

import lombok.*;
import org.letitgo.domain.beans.Album;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AlbumViewModel {

	private String albumName;
	private String username;

	public static AlbumViewModelBuilder albumViewModel() {
		return new AlbumViewModelBuilder();
	}

}
