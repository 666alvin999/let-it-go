package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.SaveMemoryForm;
import org.letitgo.application.mappers.in.SaveMemoryFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.domain.usecases.DeleteMemory;
import org.letitgo.domain.usecases.SaveMemory;
import org.letitgo.domain.usecases.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/memory")
public class MemoryController {

	private final SaveMemory saveMemory;
	private final DeleteMemory deleteMemory;
	private final UploadFile uploadFile;
	private final SaveMemoryFormMapper saveMemoryFormMapper;

	private final ActionSuccessPresenter actionSuccessPresenter;

	@Autowired
	public MemoryController(MemoryPort memoryPort, SaveMemoryFormMapper saveMemoryFormMapper, ActionSuccessPresenter actionSuccessPresenter) {
		this.saveMemory = new SaveMemory(memoryPort);
		this.deleteMemory = new DeleteMemory(memoryPort);
		this.uploadFile = new UploadFile(memoryPort);
		this.saveMemoryFormMapper = saveMemoryFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
	}

	@PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> save(@ModelAttribute SaveMemoryForm saveMemoryForm) {
		Memory memory = this.saveMemoryFormMapper.mapToMemory(saveMemoryForm);

		ActionSuccess actionSuccess = this.saveMemory.execute(memory);

		if (actionSuccess.success() && nonNull(saveMemoryForm.getMultipartFile())) {
			FileInfos fileInfos = this.saveMemoryFormMapper.mapToFileInfos(saveMemoryForm);

			if (isNull(fileInfos.file())) {
				this.deleteMemory.execute(memory);
				return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Erreur lors du transfert du fichier")));
			}

			ActionSuccess uploadFileSuccess = this.uploadFile.execute(fileInfos);

			if (!uploadFileSuccess.success()) {
				this.deleteMemory.execute(memory);
			}

			return this.actionSuccessPresenter.present(uploadFileSuccess);
		}

		return this.actionSuccessPresenter.present(actionSuccess);
	}

}