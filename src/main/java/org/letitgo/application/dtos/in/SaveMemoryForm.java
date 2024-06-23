package org.letitgo.application.dtos.in;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class SaveMemoryForm {

	private MultipartFile multipartFile;
	private String fileName;
	private String username;
	private String albumName;
	private String content;
	private String memoryDatetime;

	public static SaveMemoryFormBuilder saveMemoryForm() {
		return new SaveMemoryFormBuilder();
	}

}
