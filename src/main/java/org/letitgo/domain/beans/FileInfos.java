package org.letitgo.domain.beans;

import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.userfields.Username;

public record FileInfos(File file, AlbumName albumName, FileName fileName, Username username) {

	public FileInfos() {
		this(null, null, null, null);
	}

}
