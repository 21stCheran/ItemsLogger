package com.twentyonec.ItemsLogger.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.twentyonec.ItemsLogger.ItemsLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Storage {

	private static Storage storage = null;

	private ItemsLogger plugin = ItemsLogger.getPlugin();

	private String hostname;
	private String port;
	private String username;
	private String password;
	private String database;

	private HikariDataSource dataSource;

	private Storage(final String hostname, final String port, final String username,
			final String password, final String database) {

		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;

	}

	public static synchronized Storage getStorage(ItemsLogger plugin) {
		final Config config = plugin.getConfigManager();

		if (storage == null) {
			storage = new Storage(config.getHostname(), config.getPort(), config.getUsername(),
					config.getPassword(), config.getDatabase());
		}

		return storage;
	}

	private void connect() {

		final String jdbcUrl = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;

		HikariConfig config = new HikariConfig();

		config.setPoolName("itemslogger");
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(this.username);
		config.setPassword(this.password);

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");
		config.addDataSourceProperty("rewriteBatchedStatements", "true");
		config.addDataSourceProperty("maintainTimeStats", "false");

		config.setMaximumPoolSize(10);
		config.setMinimumIdle(10);
		config.setIdleTimeout(300000);
		config.setMaxLifetime(600000);
		config.setConnectionTimeout(5000);
		config.setInitializationFailTimeout(-1);

		try {
			this.dataSource = new HikariDataSource(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	public void update(final String sql) {

		try (Connection connection = this.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(sql);

			plugin.debugMessage("Preparing statement for update.");
			statement.executeUpdate();
			plugin.debugMessage("Successfully executed update statement. (" + sql + ")");

		} catch (SQLException e) {
			plugin.debugMessage("Error while attempting to execute update statement. (" + sql + ")");
			e.printStackTrace();
		}

	}

	public void update(String sql, Date date, Timestamp time) {

		try (Connection connection = this.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, date);
			statement.setTimestamp(2, time);

			plugin.debugMessage("Preparing statement for update.");
			statement.executeUpdate();
			plugin.debugMessage("Successfully executed update statement. (" + sql + ")");

		} catch (SQLException e) {
			plugin.debugMessage("Error while attempting to execute update statement. (" + sql + ")");
			e.printStackTrace();
		}

	}

	public ResultSet query(final String sql) {

		try (Connection connection = this.getConnection()){

			PreparedStatement statement = connection.prepareStatement(sql);

			plugin.debugMessage("Preparing statement for query.");
			ResultSet resultSet = statement.executeQuery(sql);
			plugin.debugMessage("Successfully executed query statement. (" + sql + ")");

			return resultSet;
		} catch (SQLException e) {
			plugin.debugMessage("Error while attempting to execute query statement. (" + sql + ")");
			e.printStackTrace();
			return null;
		}
	}

	public void setUpTable() {
		this.connect();

		plugin.debugMessage("Attempting to set up tables if they do not exist.");
		final String update = "CREATE TABLE IF NOT EXISTS itemslogger("
				+ "uuid VARCHAR(36) NOT NULL, "
				+ "inventory VARCHAR(21000), "
				+ "cause VARCHAR(255) NOT NULL, "
				+ "loc_x REAL NOT NULL, "
				+ "loc_y REAL NOT NULL, "
				+ "loc_z REAL NOT NULL, "
				+ "experience REAL NOT NULL, "
				+ "date DATE NOT NULL, "
				+ "time TIME NOT NULL);";
		this.update(update);
	}
}