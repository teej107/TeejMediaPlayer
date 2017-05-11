package com.teej107.mediaplayer.io.db;

import com.teej107.mediaplayer.io.AlbumManager;
import com.teej107.mediaplayer.media.audio.DatabaseSong;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class DatabaseManager
{
	private AlbumManager albumManager;
	private Path path;
	private Connection connection;
	private PreparedStatement library, libraryCount, musicInfo, addToLibrary, songByURI, albumByArtist;
	private int version;
	private Collection<CommitListener> commitListeners;

	public DatabaseManager(Path path) throws IOException
	{
		this.path = path;
		this.albumManager = albumManager;
		this.commitListeners = new HashSet<>();
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
			statement.executeUpdate(readSql(version, "init-tables"));
		}
		catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
		statement.close();
		connection.commit();

		try
		{
			library = connection.prepareStatement(readSql(version, "get-library"));
			libraryCount = connection.prepareStatement(readSql(version, "get-library-count"));
			musicInfo = connection.prepareStatement(readSql(version, "get-music-info"));
			addToLibrary = connection.prepareStatement(readSql(version, "add-to-library"));
			songByURI = connection.prepareStatement(readSql(version, "get-song-by-uri"));
			albumByArtist = connection.prepareStatement(readSql(version, "get-album-by-artist"));
		}
		catch (URISyntaxException | IOException e)
		{
			e.printStackTrace();
		}
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

			return 0;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public List<String> getColumnNames()
	{
		try
		{
			ResultSet resultSet = musicInfo.executeQuery();
			List<String> list = new ArrayList<>();
			while(resultSet.next())
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
			statement.execute(readSql(version, "purge-library"));
			connection.commit();
		}
		catch (SQLException | IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public boolean addToLibrary(Path path)
	{
		try
		{
			AudioFile f = AudioFileIO.read(path.toFile());
			Tag tag = f.getTag();
			URI uri = path.toUri();
			String title = tag.getFirst(FieldKey.TITLE);
			if(title.isEmpty())
			{
				title = Paths.get(uri).getFileName().toString();
				int index = title.lastIndexOf('.');
				if(index > -1)
				{
					title = title.substring(0, index);
				}
			}
			String artist = tag.getFirst(FieldKey.ARTIST);
			String album = tag.getFirst(FieldKey.ALBUM);
			String yearStr = tag.getFirst(FieldKey.YEAR);
			int year = 0;
			try
			{
				year = Integer.parseInt(yearStr);
			}
			catch (NumberFormatException e)
			{

			}
			int duration = f.getAudioHeader().getTrackLength();
			addToLibrary.setString(1, uri.toString());
			addToLibrary.setString(2, title);
			addToLibrary.setString(3, artist);
			addToLibrary.setString(4, album);
			addToLibrary.setInt(5, year);
			addToLibrary.setInt(6, duration);
			addToLibrary.execute();
			addToLibrary.clearParameters();
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
			for(CommitListener listener : commitListeners)
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
			while(resultSet.next())
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
			while(resultSet.next())
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
