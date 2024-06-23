package org.letitgo.infrastructure.dtos;

import lombok.*;

import java.io.InputStream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class FileInfosDTO {

	private InputStream file;
	private String fileName;

	public static FileInfosDTOBuilder fileInfosDTO() {
		return new FileInfosDTOBuilder();
	}

}
