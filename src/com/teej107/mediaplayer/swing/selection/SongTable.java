package com.teej107.mediaplayer.swing.selection;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.db.CommitListener;
import com.teej107.mediaplayer.io.db.DatabaseManager;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by teej107 on 4/21/2017.
 */
public class SongTable extends JTable implements MouseListener, CommitListener
{
	private SongTableModel songTableModel;

	public SongTable(DatabaseManager databaseManager)
	{
		this.songTableModel = new SongTableModel(databaseManager);
		setModel(songTableModel);
		addMouseListener(this);
		databaseManager.addCommitListener(this);
	}

	public void refresh()
	{
		songTableModel.refresh();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Point p = e.getPoint();
		int row = rowAtPoint(p);
		if (row != -1 && e.getClickCount() == 2)
		{
			AudioPlayer audioPlayer = Application.instance().getAudioPlayer();
			audioPlayer.setSong(songTableModel.getData().get(row));
			audioPlayer.play();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void onCommit()
	{
		SwingEDT.invoke(() ->
		{
			this.validate();
			this.repaint();
		});
	}
}
