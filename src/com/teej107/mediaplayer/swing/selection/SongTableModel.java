package com.teej107.mediaplayer.swing.selection;

import com.teej107.mediaplayer.io.db.*;
import com.teej107.mediaplayer.media.audio.DatabaseSong;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * Created by teej107 on 4/21/2017.
 */
public class SongTableModel implements TableModel, CommitListener
{
	private DatabaseManager databaseManager;
	private List<String> columns;
	private List<DatabaseSong> library;
	private int rows;

	public SongTableModel(DatabaseManager databaseManager)
	{
		this.databaseManager = databaseManager;
		this.databaseManager.addCommitListener(this);
		refresh();
	}

	public List<DatabaseSong> getData()
	{
		return library;
	}

	public void refresh()
	{
		this.columns = databaseManager.getColumnNames();
		this.library = databaseManager.getLibrary();
		this.rows = databaseManager.getLibraryCount();
	}

	@Override
	public int getRowCount()
	{
		return rows;
	}

	@Override
	public int getColumnCount()
	{
		return columns.size();
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return Column.toTableName(columns.get(columnIndex));
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return library.get(rowIndex).getRow().getObject(columnIndex, Object.class);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{

	}

	@Override
	public void addTableModelListener(TableModelListener l)
	{

	}

	@Override
	public void removeTableModelListener(TableModelListener l)
	{

	}

	@Override
	public void onCommit()
	{
		refresh();
	}
}
