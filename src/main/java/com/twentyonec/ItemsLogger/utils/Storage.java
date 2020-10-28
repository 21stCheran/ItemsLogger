package com.twentyonec.ItemsLogger.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.twentyonec.ItemsLogger.ItemPlayer;
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

	public void update(String sql, Date date, Time time) {

		try (Connection connection = this.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, date);
			statement.setTime(2, time);

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
				+ "inventory BLOB(21000), "
				+ "cause VARCHAR(255) NOT NULL, "
				+ "loc_x REAL NOT NULL, "
				+ "loc_y REAL NOT NULL, "
				+ "loc_z REAL NOT NULL, "
				+ "experience REAL NOT NULL, "
				+ "date DATE NOT NULL, "
				+ "time TIME NOT NULL);";
		this.update(update);
	}

	private ItemPlayer initializePlayer(ResultSet rs) {

		try {
			UUID uuid = UUID.fromString(rs.getString("uuid"));
			String inv = rs.getString("inventory");
			String cause = rs.getString("cause");
			int x = rs.getInt("loc_x");
			int y = rs.getInt("loc_y");
			int z = rs.getInt("loc_z");
			int experience = rs.getInt("experience");
			Date date = rs.getDate("date");
			Time time = rs.getTime("time");

			return new ItemPlayer(uuid, inv, cause, x, y, z, experience, date, time);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ItemPlayer[] retrieveList(UUID uuid, String date, String cause, Integer index) {

		index = (index == null)? 1: index;
		int max = index * 10;
		int min = max - 10;
		String sql = " SELECT * FROM itemslogger WHERE uuid = '" + uuid + "'";

		if (cause != null) {
			sql += " AND cause = '" + cause + "'";
		}

		if (date != null) {
			sql += " AND date = '" + date + "'";
		}

		sql += " ORDER BY date DESC, time DESC";
		ResultSet rs = storage.query(sql);

		try {
			List<ItemPlayer>playerDataArray = new ArrayList<ItemPlayer>();
			int i = 0;
			while (rs.next()) {
				if ((i<max) && (i >= min)) {
					playerDataArray.add(initializePlayer(rs));
				}
				i++;
			}
			return playerDataArray.toArray(new ItemPlayer[0]);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}