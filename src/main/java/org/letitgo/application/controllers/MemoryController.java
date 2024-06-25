package org.letitgo.application.controllers;

import com.google.gson.Gson;
import org.letitgo.application.dtos.in.MemoryForm;
import org.letitgo.application.mappers.in.MemoryFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.application.presenters.LocalDatePresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.domain.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/memory")
@CrossOrigin(origins = "*")
public class MemoryController {

	private final SaveMemory saveMemory;
	private final DeleteMemory deleteMemory;
	private final UploadMedia uploadMedia;
	private final DeleteMedia deleteMedia;
	private final MemoryFormMapper memoryFormMapper;

	private final ActionSuccessPresenter actionSuccessPresenter;

	private final GetDatesByUsername getDatesByUsername;
	private final LocalDatePresenter localDatePresenter;

	@Autowired
	public MemoryController(MemoryPort memoryPort, MemoryFormMapper memoryFormMapper, ActionSuccessPresenter actionSuccessPresenter, LocalDatePresenter localDatePresenter) {
		this.saveMemory = new SaveMemory(memoryPort);
		this.deleteMemory = new DeleteMemory(memoryPort);
		this.uploadMedia = new UploadMedia(memoryPort);
		this.deleteMedia = new DeleteMedia(memoryPort);
		this.getDatesByUsername = new GetDatesByUsername(memoryPort);
		this.memoryFormMapper = memoryFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
		this.localDatePresenter = localDatePresenter;
	}

	@PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> save(@ModelAttribute MemoryForm memoryForm) {
		Memory memory = this.memoryFormMapper.mapToMemory(memoryForm);

		ActionSuccess actionSuccess = this.saveMemory.execute(memory);

		if (actionSuccess.success() && nonNull(memoryForm.getMultipartFile())) {
			FileInfos fileInfos = this.memoryFormMapper.mapToFileInfos(memoryForm);

			if (isNull(fileInfos.file())) {
				this.deleteMemory.execute(memory);
				return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Erreur lors du transfert du fichier")));
			}

			ActionSuccess uploadFileSuccess = this.uploadMedia.execute(fileInfos);

			if (!uploadFileSuccess.success()) {
				this.deleteMemory.execute(memory);
			}

			return this.actionSuccessPresenter.present(uploadFileSuccess);
		}

		return this.actionSuccessPresenter.present(actionSuccess);
	}

	@PostMapping(value = "/delete", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> delete(@ModelAttribute MemoryForm memoryForm) {
		Memory memory = this.memoryFormMapper.mapToMemory(memoryForm);

		ActionSuccess actionSuccess = this.deleteMemory.execute(memory);

		if (actionSuccess.success() && nonNull(memoryForm.getFileName())) {
			FileInfos fileInfos = this.memoryFormMapper.mapToFileInfos(memoryForm);

			ActionSuccess deleteFileSuccess = this.deleteMedia.execute(fileInfos);

			if (!deleteFileSuccess.success()) {
				this.actionSuccessPresenter.present(this.saveMemory.execute(memory));
				return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Une erreur est survenue lors de la suppression de votre Memory. Veuillez réessayer plus tard.")));
			}

			return this.actionSuccessPresenter.present(deleteFileSuccess);
		}

		return this.actionSuccessPresenter.present(actionSuccess);
	}

	@GetMapping("/getDatesByUsername")
	public ResponseEntity<String> getDatesByUsername(@RequestParam String username) {
		Set<LocalDate> localDates = this.getDatesByUsername.execute(username);
		return this.localDatePresenter.presentAll(localDates);
	}

}