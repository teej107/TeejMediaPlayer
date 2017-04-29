package com.teej107.mediaplayer.swing.action;

import com.sun.javafx.application.PlatformImpl;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.AudioFileVisitor;
import javafx.stage.DirectoryChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by teej107 on 4/23/2017.
 */
public class ImportMusicAction extends AbstractAction
{
	private DirectoryChooser fileChooser;

	public ImportMusicAction()
	{
		super("Import Music");
		this.fileChooser = new DirectoryChooser();
		this.fileChooser.setTitle((String) getValue(Action.NAME));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		PlatformImpl.runLater(() ->
		{
			File file = fileChooser.showDialog(null);
			if (file == null)
				return;

			Path path = file.toPath();
			Application.instance().getDatabaseManager().purgeLibrary();
			try
			{
				Files.walkFileTree(path, new AudioFileVisitor());
				Application.instance().getDatabaseManager().commit();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		});
	}
}
