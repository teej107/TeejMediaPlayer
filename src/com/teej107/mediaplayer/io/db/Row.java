package com.teej107.mediaplayer.io.db;

import java.sql.*;
import java.util.*;

/**
 * Created by teej107 on 4/15/17.
 */
public class Row extends LinkedHashMap<String, Object>
{
	private List<String> columns;

	public Row(ResultSet set) throws SQLException
	{
		ResultSetMetaData meta = set.getMetaData();
		for (int i = 1; i <= meta.getColumnCount(); i++)
		{
			put(meta.getColumnName(i), set.getObject(i));
		}
		this.columns = new ArrayList<>(getColumns());
	}

	public Set<String> getColumns()
	{
		return this.keySet();
	}

	public <T> T getObject(String column, Class<T> t)
	{
		Object o = get(column);
		if (o == null)
			return null;
		if (t.isAssignableFrom(o.getClass()))
			return (T) o;

		throw new IllegalArgumentException("Class " + t.getName() + " not of type " + o.getClass().getName());
	}

	public <T> T getObject(int column, Class<T> t)
	{
		return getObject(columns.get(column), t);
	}
}
