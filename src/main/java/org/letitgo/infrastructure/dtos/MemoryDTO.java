package org.letitgo.infrastructure.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MemoryDTO {

	private String username;
	private String textContent;
	private String mediaName;
	private String memoryDatetime;

	public static MemoryDTOBuilder memoryDTO() {
		return new MemoryDTOBuilder();
	}

}
