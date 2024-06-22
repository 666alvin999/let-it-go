package org.letitgo.application.controllers;

import org.letitgo.application.mappers.in.SaveFileFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.domain.usecases.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/memory")
public class MemoryController {

	private final UploadFile uploadFile;
	private final SaveFileFormMapper saveFileFormMapper;

	private final ActionSuccessPresenter actionSuccessPresenter;

	@Autowired
	public MemoryController(MemoryPort memoryPort, SaveFileFormMapper saveFileFormMapper, ActionSuccessPresenter actionSuccessPresenter) {
		this.uploadFile = new UploadFile(memoryPort);
		this.saveFileFormMapper = saveFileFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam String username, @RequestParam String fileName) {
		FileInfos fileInfos = this.saveFileFormMapper.mapToFileInfos(multipartFile, username, fileName);

		if (isNull(fileInfos.file())) {
			return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Erreur de transfert du média.")));
		}

		ActionSuccess actionSuccess = this.uploadFile.execute(fileInfos);

		return this.actionSuccessPresenter.present(actionSuccess);
	}

}
