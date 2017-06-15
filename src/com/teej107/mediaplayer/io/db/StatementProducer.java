package com.teej107.mediaplayer.io.db;

import com.teej107.mediaplayer.util.Util;

import static com.teej107.mediaplayer.io.db.Column.ALBUM;
import static com.teej107.mediaplayer.io.db.Column.ARTIST;
import static com.teej107.mediaplayer.io.db.Column.URI;

/**
 * Created by teej107 on 6/13/2017.
 */
public class StatementProducer
{
	private String table;

	public StatementProducer(String table)
	{
		this.table = table;
	}

	public String initializeTable()
	{
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(table).append('(');
		Column[] columns = Column.values();
		for (int i = 0; i < columns.length; i++)
		{
			sb.append(columns[i].getDeclaration());
			if (i < columns.length - 1)
			{
				sb.append(',');
			}
		}
		return sb.append(')').toString();
	}

	public String purgeLibrary()
	{
		return "DELETE FROM " + table;
	}

	public String getSongByURI()
	{
		return "SELECT * FROM " + table + " WHERE " + URI + " = ?";
	}

	public String getTableInfo()
	{
		return "PRAGMA table_info(" + table + ")";
	}

	public String getLibraryCount()
	{
		return "SELECT count(*) FROM " + table;
	}

	public String getLibrary()
	{
		return "SELECT * FROM " + table + " ORDER BY ?";
	}

	public String getArtists()
	{
		return "SELECT DISTINCT " + ARTIST + " FROM " + table + " ORDER BY " + ARTIST;
	}

	public String getAlbumByArtist()
	{
		return "SELECT * FROM " + table + " WHERE " + ARTIST + " = ? AND " + ALBUM + " = ?";
	}

	public String addToLibrary()
	{
		Column[] values = Column.values();
		return "INSERT INTO " + table + " (" + Util.toString(values, ", ") +
				") VALUES (" + Util.repeat("?", values.length, ", ") + ")";
	}

	public String updateRow(String row)
	{
		return "UPDATE " + table + " SET " + row + "=? WHERE " + Column.URI + " = ?";
	}
}
