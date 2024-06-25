package org.letitgo.domain.beans;

import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.Username;

public record ProfilePictureInfos(File file, Username username, Extension extension) {

	public ProfilePictureInfos() {
		this(null, null, null);
	}

}
