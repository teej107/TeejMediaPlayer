package com.teej107.mediaplayer.io.db;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class DatabaseManager
{
	private Path path;
	private Connection connection;

	public DatabaseManager(Path path) throws IOException
	{
		this.path = path;
		try
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + path.toAbsolutePath());
		}
		catch (ClassNotFoundException | SQLException e)
		{
			throw new IOException(e);
		}
	}
}
