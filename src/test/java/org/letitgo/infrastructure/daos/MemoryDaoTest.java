package org.letitgo.infrastructure.daos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.letitgo.utils.BasicDatabaseExtension;
import org.letitgo.utils.EzDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.file.Paths;
import java.util.HashMap;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(BasicDatabaseExtension.class)
class MemoryDaoTest {

	private MemoryDao memoryDao;

	@EzDatabase
	private NamedParameterJdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		this.memoryDao = new MemoryDao();
		setField(this.memoryDao, "jdbcTemplate", this.jdbcTemplate);

		initTables();
	}

	@Test
	public void shouldSaveMemory() {
	    // Arrange
		MemoryDTO memoryDTO = memoryDTO()
			.username("ahamaide")
			.textContent("as;dlfj;aldjsf;")
			.memoryDatetime("2023-12-31:12-45-03")
			.build();

	    // Act
		ActionSuccess actualActionSuccess = this.memoryDao.saveMemory(memoryDTO);

	    // Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@SneakyThrows
	private void initTables() {
		this.jdbcTemplate.update(
			new String(readAllBytes(Paths.get("src/test/resources/memory_init.sql"))),
			new HashMap<>()
		);
	}

}