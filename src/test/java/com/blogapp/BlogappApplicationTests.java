package com.blogapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class BlogAppApplicationTests {
	private final DataSource datasource;
	@Autowired
	BlogAppApplicationTests(DataSource dataSource){
		this.datasource = dataSource;
	}
//throws SQLException
	@Test
	void applicationCanConnectToDatabaseTest() {
		assertThat(datasource).isNotNull();
		log.info("Datasource object is created");

		try(Connection connection = datasource.getConnection()){
			assertThat(connection).isNotNull();
			log.info("Connection --> {}", connection.getCatalog());
			assertThat(connection.getCatalog()).isEqualTo("blogdb");
		}catch (SQLException throwables) {

			log.info("Exception occured while connectiong to the database -->{}", throwables.getMessage());
		}
	}

}
