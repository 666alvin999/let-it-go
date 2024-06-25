package org.letitgo.domain.beans;

import org.letitgo.domain.beans.memoryfields.*;
import org.letitgo.domain.beans.userfields.Username;

public record Memory(AlbumName albumName, Username username, Content content, MediaName mediaName,
                     MemoryDatetime memoryDatetime, Mood mood) {
}
