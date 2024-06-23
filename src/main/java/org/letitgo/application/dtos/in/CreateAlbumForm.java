package org.letitgo.application.dtos.in;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CreateAlbumForm {

	private String albumName;
	private String username;

	public static CreateAlbumFormBuilder createAlbumForm() {
		return new CreateAlbumFormBuilder();
	}

}
