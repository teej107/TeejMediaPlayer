package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;
import com.teej107.mediaplayer.swing.menu.ImportMusicAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 4/21/2017.
 */
public class ApplicationMenu extends JMenuBar
{
	public ApplicationMenu()
	{
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new ImportMusicAction()));
		file.addSeparator();
		file.add(new AbstractAction(Platform.getPlatform().getTerminate())
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Application.instance().exit();
			}
		});
		add(file);
	}
}
