package org.letitgo.application.dtos.in;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AlbumForm {

	private String albumName;
	private String username;

	public static AlbumFormBuilder createAlbumForm() {
		return new AlbumFormBuilder();
	}

}
