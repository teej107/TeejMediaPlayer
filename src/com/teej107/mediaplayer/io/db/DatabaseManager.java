package com.teej107.mediaplayer.io.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.sql.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class DatabaseManager
{
	private Path path;
	private Connection connection;
	private int version;

	public DatabaseManager(Path path) throws IOException
	{
		this.path = path;
		try
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + path.toAbsolutePath());
			connection.setAutoCommit(false);
			init();
		}
		catch (ClassNotFoundException | SQLException e)
		{
			throw new IOException(e);
		}
	}

	private void init() throws SQLException
	{
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery("PRAGMA USER_VERSION");
		version = set.getInt(1);
		try
		{
			statement.executeUpdate(readSql(0, "init-tables"));
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		statement.close();
		connection.commit();
	}

	private static String getSqlDir(int version)
	{
		return "/com/teej107/mediaplayer/io/db/sql/_" + version;
	}

	private static String readSql(int version, String name) throws URISyntaxException, IOException
	{
		URL url = DatabaseManager.class.getResource(getSqlDir(version) + "/" + name + ".sql");
		return new String(Files.readAllBytes(Paths.get(url.toURI())));
	}
}
