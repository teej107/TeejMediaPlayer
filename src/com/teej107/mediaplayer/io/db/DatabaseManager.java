package com.teej107.mediaplayer.io.db;

import com.teej107.mediaplayer.media.audio.DatabaseSong;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class DatabaseManager
{
	private static final String TABLE_NAME = "music";

	private Path path;
	private Connection connection;
	private PreparedStatement library, libraryCount, musicInfo, addToLibrary, songByURI, albumByArtist, artists, updateRow;
	private Collection<CommitListener> commitListeners;
	private StatementProducer statementProducer;

	public DatabaseManager(Path path) throws IOException
	{
		this.path = path;
		this.commitListeners = new HashSet<>();
		this.statementProducer = new StatementProducer(TABLE_NAME);
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
		statement.executeUpdate(statementProducer.initializeTable());

		musicInfo = connection.prepareStatement(statementProducer.getTableInfo());

		List<String> columns = getColumnNames();
		for (Column c : Column.values())
		{
			if (!columns.contains(c.getDatabaseName()))
			{
				c.addColumn(TABLE_NAME, statement);
			}
		}

		library = connection.prepareStatement(statementProducer.getLibrary());
		libraryCount = connection.prepareStatement(statementProducer.getLibraryCount());
		addToLibrary = connection.prepareStatement(statementProducer.addToLibrary());
		songByURI = connection.prepareStatement(statementProducer.getSongByURI());
		albumByArtist = connection.prepareStatement(statementProducer.getAlbumByArtist());
		artists = connection.prepareStatement(statementProducer.getArtists());
		//updateRow = connection.prepareStatement(statementProducer.updateRow());

		statement.close();
		connection.commit();
	}

	public Path getPath()
	{
		return path;
	}

	public boolean addCommitListener(CommitListener listener)
	{
		return commitListeners.add(listener);
	}

	public int getLibraryCount()
	{
		try
		{
			ResultSet countSet = libraryCount.executeQuery();
			if (countSet.next())
				return countSet.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public List<String> getColumnNames()
	{
		try
		{
			ResultSet resultSet = musicInfo.executeQuery();
			List<String> list = new ArrayList<>();
			while (resultSet.next())
			{
				list.add(resultSet.getString("name"));
			}
			return list;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public void purgeLibrary()
	{
		try
		{
			Statement statement = connection.createStatement();
			statement.execute(statementProducer.purgeLibrary());
			connection.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean addToLibrary(Path path)
	{
		try
		{
			AudioFile f = AudioFileIO.read(path.toFile());
			addToLibrary.clearParameters();
			for(Column c : Column.values())
			{
				c.setQuery(addToLibrary, path, f);
			}
			addToLibrary.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void commit()
	{
		try
		{
			connection.commit();
			for (CommitListener listener : commitListeners)
			{
				listener.onCommit();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public DatabaseSong getSongByURI(URI uri)
	{
		try
		{
			songByURI.setString(1, uri.toString());
			ResultSet resultSet = songByURI.executeQuery();
			songByURI.clearParameters();
			DatabaseSong song = null;
			while (resultSet.next())
			{
				song = new DatabaseSong(new Row(resultSet));
			}
			resultSet.close();
			return song;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public List<DatabaseSong> getLibrary(OrderBy order)
	{
		try
		{
			int count = getLibraryCount();
			List<DatabaseSong> collection = new ArrayList<>(count);
			library.setString(1, order.toString());
			ResultSet resultSet = library.executeQuery();
			library.clearParameters();
			while (resultSet.next())
			{
				collection.add(new DatabaseSong(new Row(resultSet)));
			}
			resultSet.close();
			return collection;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<DatabaseSong> getAlbumByArtist(String artist, String album)
	{
		List<DatabaseSong> list = new ArrayList<>();
		try
		{
			albumByArtist.setString(1, artist);
			albumByArtist.setString(2, album);
			ResultSet resultSet = albumByArtist.executeQuery();
			albumByArtist.clearParameters();
			while (resultSet.next())
			{
				list.add(new DatabaseSong(new Row(resultSet)));
			}
			resultSet.close();
			return list;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List<DatabaseSong> getLibrary()
	{
		return getLibrary(OrderBy.ARTIST);
	}

	public List<String> getArtists()
	{
		List<String> list = new ArrayList<>();
		try (ResultSet result = artists.executeQuery())
		{
			while (result.next())
			{
				list.add(result.getString(1));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public enum OrderBy
	{
		ARTIST("artist"),
		TITLE("title"),
		ALBUM("album");

		private String order;

		OrderBy(String order)
		{
			this.order = order;
		}

		@Override
		public String toString()
		{
			return order;
		}
	}
}
