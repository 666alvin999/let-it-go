package org.letitgo.domain.beans;

import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.userfields.Username;

public record FileInfos(File file, FileName fileName, Username username) {

	public FileInfos() {
		this(null, null, null);
	}

}
