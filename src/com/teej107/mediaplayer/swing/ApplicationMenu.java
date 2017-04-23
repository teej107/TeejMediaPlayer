package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.AudioFileVisitor;
import com.teej107.mediaplayer.platform.Platform;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by teej107 on 4/21/2017.
 */
public class ApplicationMenu extends JMenuBar
{
	private JFileChooser fileChooser;

	public ApplicationMenu()
	{
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Path directory = Application.instance().getApplicationPreferences().getMusicRootDirectory();
		if (directory != null)
		{
			fileChooser.setCurrentDirectory(directory.toFile());
		}

		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new AbstractAction("Import Music")
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (fileChooser.showDialog(SwingUtilities.getRootPane(ApplicationMenu.this), "Import") == JFileChooser.APPROVE_OPTION)
				{
					Path path = fileChooser.getSelectedFile().toPath();
					if (directory != null && !path.equals(directory))
					{
						Application.instance().getDatabaseManager().purgeLibrary();
						try
						{
							Files.walkFileTree(path, new AudioFileVisitor());
							Application.instance().getDatabaseManager().commit();
							//Application.instance().getApplicationPreferences().setMusicRootDirectory(path);
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
		}));
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
