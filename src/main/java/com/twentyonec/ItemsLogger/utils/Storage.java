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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Storage {

	private static Storage storage;
	private final ItemsLogger plugin = ItemsLogger.getPlugin();

	private final String hostname;
	private final String port;
	private final String username;
	private final String password;
	private final String database;

	private HikariDataSource dataSource;

	private Storage(final String hostname, final String port, final String username,
			final String password, final String database) {

		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
	}

	public static synchronized Storage getStorage(final ItemsLogger plugin) {

		if (storage == null) {
			final Config config = plugin.getConfigManager();
			storage = new Storage(config.getHostname(), config.getPort(), config.getUsername(),
					config.getPassword(), config.getDatabase());
		}

		return storage;
	}

	public static synchronized Storage getNewStorage(final ItemsLogger plugin) {

		final Config config = plugin.getConfigManager();
		storage = new Storage(config.getHostname(), config.getPort(), config.getUsername(),
				config.getPassword(), config.getDatabase());

		return storage;
	}

	private void connect() {

		final String jdbcUrl = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;

		final HikariConfig config = new HikariConfig();

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
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	private Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
	
	public void closeConnection() throws SQLException {
		this.dataSource.close();
	}

	public void update(final String sql, final Object... args) {

		plugin.debugMessage("Preparing statement for update");
		try (Connection connection = this.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			for (int i = 1; i <= args.length; i++) {
				statement.setObject(i, args[i - 1]);
			}

			statement.executeUpdate();
			plugin.debugMessage("Successfully executed update statement. (" + sql + ")");

		} catch (final SQLException e) {
			plugin.debugMessage("Error while attempting to execute statement update. (" + sql + ")");
			e.printStackTrace();
		}

	}

	public ResultSet query(final String sql, final Object... args) {

		try (Connection connection = this.getConnection()){

			final PreparedStatement statement = connection.prepareStatement(sql);

			plugin.debugMessage("Preparing statement for query.");
			for (int i = 1; i <= args.length; i++) {
				statement.setObject(i, args[i-1]);
			}

			final ResultSet resultSet = statement.executeQuery();
			plugin.debugMessage("Successfully executed query statement. (" + sql + ")");

			return resultSet;
		} catch (final SQLException e) {
			plugin.debugMessage("Error while attempting to execute query statement. (" + sql + ")");
			e.printStackTrace();
			return null;
		}
	}

	public void setUpTable() {
		this.connect();

		plugin.debugMessage("Attempting to set up tables if they do not exist.");
		final String createTableQuery = "CREATE TABLE IF NOT EXISTS itemslogger("
				+ "uuid VARCHAR(36) NOT NULL, "
				+ "inventory TEXT NOT NULL, "
				+ "cause VARCHAR(255) NOT NULL, "
				+ "loc_x REAL NOT NULL, "
				+ "loc_y REAL NOT NULL, "
				+ "loc_z REAL NOT NULL, "
				+ "experience REAL NOT NULL, "
				+ "date DATE NOT NULL, "
				+ "time TIME NOT NULL);";
		this.update(createTableQuery);
	}

	private ItemPlayer initializePlayer(final ResultSet rs) {

		try {
			final UUID uuid = UUID.fromString(rs.getString("uuid"));
			final String inv = rs.getString("inventory");
			final String cause = rs.getString("cause");
			final int x = rs.getInt("loc_x");
			final int y = rs.getInt("loc_y");
			final int z = rs.getInt("loc_z");
			final int experience = rs.getInt("experience");
			final Date date = rs.getDate("date");
			final Time time = rs.getTime("time");

			return new ItemPlayer(uuid, inv, cause, x, y, z, experience, date, time);
		} catch (final SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private CompletableFuture<ItemPlayer[]> retrieveLogListAsync(final UUID uuid, final String date, final String cause, final int max, final int min) {

		return CompletableFuture.supplyAsync(() -> {
			final String sql = " SELECT * FROM itemslogger WHERE uuid = ?";

			final StringBuilder sb = new StringBuilder(sql);
			final List<Object> args = new ArrayList<Object>();
			args.add(uuid.toString());
			if (date != null) {
				sb.append(" AND date = ?");
				args.add(date);
			}
			if (cause != null) {
				sb.append(" AND cause = ?");
				args.add(cause);
			}
			sb.append(" ORDER BY date DESC, time DESC");
			final ResultSet rs = storage.query(sb.toString(), args.toArray(new Object[0]));

			try {
				final List<ItemPlayer>playerDataArray = new ArrayList<ItemPlayer>();
				int i = 0;
				while (rs.next()) {
					if ((i<max) && (i >= min)) {
						playerDataArray.add(initializePlayer(rs));
					}
					i++;
				}
				return playerDataArray.toArray(new ItemPlayer[0]);
			} catch (final SQLException e) {
				e.printStackTrace();
				return null;
			}
		});

	}

	public ItemPlayer[] retrieveLogList(final UUID uuid, final String date, final String cause, final int index) {

		final int max = index * 10;
		final int min = max - 10;

		try {
			return retrieveLogListAsync(uuid, date, cause, max, min).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}

	}

	private CompletableFuture<ItemPlayer>  retrieveItemPlayerAsync(final UUID uuid, final String date, final String time) {

		return CompletableFuture.supplyAsync(() -> {
			final String sql = " SELECT * FROM itemslogger WHERE uuid = ?"
					+ " AND date = ? AND time = ?";

			final ResultSet rs = storage.query(sql, uuid.toString(), date, time);

			try {
				while (rs.next()) {
					return initializePlayer(rs);
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			return null;
		});
	}

	public ItemPlayer retrieveItemPlayer(final UUID uuid, final String date, final String time) {

		try {
			return retrieveItemPlayerAsync(uuid, date, time).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteLogs(final int days) {
		plugin.debugMessage("Attempting to delete logs older than " + days + " days.");
		final String sql = "DELETE FROM itemslogger WHERE DATEDIFF(CURDATE(), date) > ?";
		this.update(sql, days);
	}
}