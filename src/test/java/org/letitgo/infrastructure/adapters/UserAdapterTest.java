package org.letitgo.infrastructure.adapters;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.daos.UserDao;
import org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.infrastructure.mappers.ProfilePictureInfosMapper;
import org.letitgo.infrastructure.mappers.UserMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO.profilePictureInfosDTO;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

	private UserAdapter userAdapter;

	@Mock
	private UserDao userDao;

	@Mock
	private DropboxDao dropboxDao;

	@Mock
	private UserMapper userMapper;

	@Mock
	private ProfilePictureInfosMapper profilePictureInfosMapper;

	@BeforeEach
	public void setUp() {
		this.userAdapter = new UserAdapter(this.userDao, this.dropboxDao, this.userMapper, this.profilePictureInfosMapper);
	}

	@Test
	public void shouldRegisterSuccessfully() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new Mail("mail"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password"),
			ColorTheme.URANUS,
			new ProfilePicture(null)
		);

		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.colorTheme("uranus")
			.profilePicture(null)
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.register(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.register(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInSuccessfully() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new Mail(null),
			new BirthDate(null),
			null,
			new Password("password"),
			ColorTheme.NULL,
			new ProfilePicture(null)
		);

		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.pwd("password")
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.logUserIn(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.logUserIn(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	@SneakyThrows
	public void shouldUploadProfilePictureFileSuccessfully() {
		// Arrange
		ProfilePictureInfos profilePictureInfos = new ProfilePictureInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new Username("ahamaide"),
			new Extension("png")
		);

		ProfilePictureInfosDTO profilePictureInfosDTO = new ProfilePictureInfosDTO(
			new FileInputStream("src/test/resources/test_img.png"),
			"/ahamaide/ahamaide.png"
		);

		when(this.profilePictureInfosMapper.mapToDTO(profilePictureInfos)).thenReturn(profilePictureInfosDTO);
		when(this.dropboxDao.uploadFile(profilePictureInfosDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.uploadProfilePictureFile(profilePictureInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	@SneakyThrows
	public void shouldDeleteProfilePictureFileSuccessfully() {
		// Arrange
		ProfilePictureInfos profilePictureInfos = new ProfilePictureInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new Username("ahamaide"),
			new Extension("png")
		);

		ProfilePictureInfosDTO profilePictureInfosDTO = new ProfilePictureInfosDTO(
			new FileInputStream("src/test/resources/test_img.png"),
			"/ahamaide/ahamaide.png"
		);

		when(this.profilePictureInfosMapper.mapToDTO(profilePictureInfos)).thenReturn(profilePictureInfosDTO);
		when(this.dropboxDao.deleteFile(profilePictureInfosDTO.getFileName())).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.deleteProfilePictureFile(profilePictureInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldInsertProfilePictureInfosSuccessfully() {
		// Arrange
		ProfilePictureInfos profilePictureInfos = new ProfilePictureInfos(
			null,
			new Username("ahamaide"),
			new Extension("png")
		);

		ProfilePictureInfosDTO profilePictureInfosDTO = profilePictureInfosDTO()
			.fileName("/ahamaide/ahamaide.png")
			.build();

		when(this.profilePictureInfosMapper.mapToDTO(profilePictureInfos)).thenReturn(profilePictureInfosDTO);
		when(this.userDao.insertProfilePictureByUsername(profilePictureInfosDTO.getFileName(), profilePictureInfos.username().value())).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.insertProfilePictureInfosByUsername(profilePictureInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDeleteProfilePictureInfosSuccessfully() {
		// Arrange
		String username = "ahamaide";

		when(this.userDao.deleteProfilePictureByUsername(username)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.deleteProfilePictureInfosByUsername(username);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInSuccessfully_whenMailIsGiven() {
		// Arrange
		User user = new User(
			new Username(null),
			new Mail("mail"),
			new BirthDate(null),
			null,
			new Password("password"),
			ColorTheme.NULL,
			new ProfilePicture(null)
		);

		UserDTO userDTO = userDTO()
			.mail("mail")
			.pwd("password")
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.logUserIn(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.logUserIn(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnUsernameIsFree() {
		// Arrange
		String username = "ahamaide";

		when(this.userDao.isUsernameFree(username)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.isUsernameFree(username);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnMailIsFree() {
		// Arrange
		String mail = "mail";

		when(this.userDao.isMailFree(mail)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.isMailFree(mail);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}