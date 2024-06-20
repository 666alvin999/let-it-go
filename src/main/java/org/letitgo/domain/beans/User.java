package org.letitgo.domain.beans;

import org.letitgo.domain.beans.userfields.*;

public record User(Username username, BirthDate birthDate, Identity identity, Password password) {
}
