package org.letitgo.infrastructure.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AlbumDTO {

	private String albumName;
	private String username;

	public static AlbumDTOBuilder albumDTO() {
		return AlbumDTO.builder();
	}

}
