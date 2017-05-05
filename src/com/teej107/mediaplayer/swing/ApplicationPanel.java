package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.swing.controls.MusicInfoControlPanel;
import com.teej107.mediaplayer.swing.selection.SongTable;
import com.teej107.mediaplayer.util.Util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class ApplicationPanel extends JPanel
{
	private JSplitPane splitPane;
	private MusicInfoControlPanel musicInfoControlPanel;
	private SongTable songTable;
	private ApplicationStatusBar statusBar;

	public ApplicationPanel()
	{
		super(new BorderLayout());
		this.musicInfoControlPanel = new MusicInfoControlPanel(Application.instance().getAudioPlayer(),
				Application.instance().getAlbumManager());
		this.songTable = new SongTable(Application.instance().getDatabaseManager());

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				musicInfoControlPanel, new JScrollPane(songTable));
		add(splitPane, BorderLayout.CENTER);

		this.statusBar = new ApplicationStatusBar();
		add(statusBar, BorderLayout.PAGE_END);

		Util.setChildrenUnfocusable(this);
	}

	public ApplicationStatusBar getStatusBar()
	{
		return statusBar;
	}
}
