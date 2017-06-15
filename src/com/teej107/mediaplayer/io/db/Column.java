package com.teej107.mediaplayer.io.db;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.ITagFetcher;
import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.media.audio.ISongJSONMapper;
import com.teej107.mediaplayer.util.Util;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * Created by teej107 on 6/11/2017.
 */
public enum Column
{
	URI("uri", "TEXT", true, "Path", (song, map) ->
	{
		Path songPath = Paths.get(song.getURI());
		String urlPath = Application.instance().getApplicationPreferences().getMusicRootDirectory().relativize(songPath).toString();
		map.put("path", urlPath.replace("\\", "/"));
	}, (path, af) -> path.toUri().toString()),

	TITLE("title", "TEXT", "Title", (song, map) -> map.put("title", song.getTitle()), (path, af) ->
	{
		String title = af.getTag().getFirst(FieldKey.TITLE);
		if (title.isEmpty())
		{
			title = path.getFileName().toString();
			int index = title.lastIndexOf('.');
			if (index > -1)
				return title.substring(0, index);
		}
		return title;
	}),

	ARTIST("artist", "TEXT", "Artist", (song, map) -> map.put("artist", song.getArtist()),
			(path, af) -> af.getTag().getFirst(FieldKey.ARTIST)),

	ALBUM("album", "TEXT", "Album", (song, map) -> map.put("album", song.getAlbum()), (path, af) -> af.getTag().getFirst(FieldKey.ALBUM)),

	GENRE("genre", "TEXT", "Genre", (song, map) -> map.put("genre", song.getGenre()), (path, af) -> af.getTag().getFirst(FieldKey.GENRE)),

	YEAR("year", "INT", "Year", (song, map) -> map.put("year", song.getYear()),
			(path, af) -> Util.toInt(af.getTag().getFirst(FieldKey.YEAR), 0)),

	TRACK_NUMBER("track_number", "INT", "Track Number", (song, map) -> map.put("track number", song.getTrackNumber()),
			(path, af) -> Util.toInt(af.getTag().getFirst(FieldKey.TRACK), 0)),

	DURATION_SEC("duration_sec", "INT", "Duration", (song, map) -> map.put("duration", song.getDuration()),
			(path, af) -> af.getAudioHeader().getTrackLength());

	static final Map<String, String> COLUMN_TO_TABLE;

	String columnName, friendlyName, type;
	boolean primaryKey;
	ITagFetcher<?> tagFetcher;
	ISongJSONMapper mapper;

	Column(String columnName, String type, String friendlyName, ISongJSONMapper mapper, ITagFetcher<?> tagFetcher)
	{
		this(columnName, type, false, friendlyName, mapper, tagFetcher);
	}

	Column(String columnName, String type, boolean primaryKey, String friendlyName, ISongJSONMapper mapper, ITagFetcher<?> tagFetcher)
	{
		this.columnName = columnName;
		this.type = type;
		this.primaryKey = primaryKey;
		this.friendlyName = friendlyName;
		this.mapper = mapper;
		this.tagFetcher = tagFetcher;
	}

	public boolean isPrimaryKey()
	{
		return primaryKey;
	}

	public String getType()
	{
		return type;
	}

	public String getTableName()
	{
		return friendlyName;
	}

	public String getDatabaseName()
	{
		return columnName;
	}

	public void addColumn(String table, Statement statement) throws SQLException
	{
		statement.executeUpdate("ALTER TABLE " + table + " ADD " + columnName + " " + type + " NULL");
	}

	public void setQuery(PreparedStatement statement, Path path, AudioFile af) throws SQLException
	{
		if (type.equals("INT"))
		{
			Integer num = (Integer) tagFetcher.getData(path, af);
			statement.setInt(ordinal() + 1, num.intValue());
		}
		else
		{
			statement.setString(ordinal() + 1, (String) tagFetcher.getData(path, af));
		}
	}

	public void insertJSON(ISong song, Map<String, Object> map)
	{
		this.mapper.map(song, map);
	}

	public String getDeclaration()
	{
		StringBuilder sb = new StringBuilder(this.columnName).append(' ').append(this.type);
		if (primaryKey)
		{
			sb.append(" PRIMARY KEY");
		}
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return this.columnName;
	}

	public static String toTableName(String name)
	{
		return COLUMN_TO_TABLE.get(name.toLowerCase());
	}

	static
	{
		Map<String, String> columnToTable = new HashMap<>();
		for (Column c : values())
		{
			columnToTable.put(c.columnName.toLowerCase(), c.friendlyName);
		}
		COLUMN_TO_TABLE = Collections.unmodifiableMap(columnToTable);
	}
}
