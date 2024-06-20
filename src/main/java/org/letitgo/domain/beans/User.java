package org.letitgo.domain.beans;

import org.letitgo.domain.beans.userfields.*;

public record User(Username username, Mail mail, BirthDate birthDate, Identity identity, Password password) {
}
