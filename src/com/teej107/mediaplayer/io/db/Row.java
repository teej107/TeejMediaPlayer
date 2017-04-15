package com.teej107.mediaplayer.io.db;

import java.sql.*;
import java.util.*;

/**
 * Created by teej107 on 4/15/17.
 */
public class Row extends LinkedHashMap<String, Object>
{
	public Row(ResultSet set) throws SQLException
	{
		ResultSetMetaData meta = set.getMetaData();
		for (int i = 0; i < meta.getColumnCount(); i++)
		{
			put(meta.getColumnName(i), set.getObject(i));
		}
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
}
