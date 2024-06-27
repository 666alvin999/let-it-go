package org.letitgo.domain.beans;

import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.memoryfields.Mood;
import org.letitgo.domain.beans.userfields.Username;

public record Memory(AlbumName albumName, Username username, Content content, MediaName mediaName,
                     MemoryDatetime memoryDatetime, Mood mood) {
}
