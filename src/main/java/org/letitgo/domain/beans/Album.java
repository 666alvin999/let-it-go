package org.letitgo.domain.beans;

import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;

public record Album(AlbumName albumName, Username username) {
}
