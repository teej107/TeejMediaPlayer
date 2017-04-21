package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.swing.component.MusicInfoControlPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class ApplicationPanel extends JPanel
{
	private JSplitPane splitPane;

	public ApplicationPanel()
	{
		super(new BorderLayout());
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				new MusicInfoControlPanel(Application.instance().getAudioPlayer()), new JPanel());
		add(splitPane, BorderLayout.CENTER);
	}
}
