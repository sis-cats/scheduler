package fr.ca.cats.logging.oapp.scheduler.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;

public class SimpleConnectionProvider implements ConnectionProvider {

	@Override
	public Connection getConnection() throws SQLException {
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/quartz-test?user=root&password=");
		return c;
	}

	@Override
	public void shutdown() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() throws SQLException {
		// TODO Auto-generated method stub

	}

}
