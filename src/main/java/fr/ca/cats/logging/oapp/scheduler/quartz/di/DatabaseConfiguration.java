package fr.ca.cats.logging.oapp.scheduler.quartz.di;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import fr.ca.cats.logging.oapp.scheduler.quartz.dao.JobDao;

@Configuration
@PropertySource("classpath:/datasource.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private Environment environment;

	///////////////////////////////////////////////////////////////////////////
	///
	/// DataSource related beans
	///
	///////////////////////////////////////////////////////////////////////////
	
	@Bean(name = "simpleMysqlDataSource")
	public DataSource simpleMysqlDataSource() throws SQLException {
		MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(environment.getProperty("db.url"));
        mysqlDS.setUser(environment.getProperty("db.user"));
        mysqlDS.setPassword(environment.getProperty("db.password"));
		return mysqlDS;
	}
	
	@Bean(name = "simpleLocalConnectionFactory")
	public Connection localConnection() throws SQLException {
		return DriverManager.getConnection(environment.getProperty("db.url"));
	}
	
	///////////////////////////////////////////////////////////////////////////
	///
	/// DAO related beans
	///
	///////////////////////////////////////////////////////////////////////////
	
	@Bean
	public JobDao jobDao() {
		throw new RuntimeException("Not implemented yet");
	}
}
