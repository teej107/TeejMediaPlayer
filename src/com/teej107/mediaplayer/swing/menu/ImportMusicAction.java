package com.teej107.mediaplayer.swing.menu;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.AudioFileVisitor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by teej107 on 4/23/2017.
 */
public class ImportMusicAction extends AbstractAction
{
	private JFileChooser fileChooser;

	public ImportMusicAction()
	{
		super("Import Music");
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (fileChooser.showDialog(null, "Import") == JFileChooser.APPROVE_OPTION)
		{
			Path path = fileChooser.getSelectedFile().toPath();
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
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}
}
