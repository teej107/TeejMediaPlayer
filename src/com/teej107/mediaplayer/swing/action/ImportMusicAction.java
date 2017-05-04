package com.teej107.mediaplayer.swing.action;

import com.sun.javafx.application.PlatformImpl;
import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.io.AudioFileVisitor;
import com.teej107.mediaplayer.io.db.DatabaseManager;
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
	private DatabaseManager databaseManager;
	private ApplicationPreferences applicationPreferences;
	private DirectoryChooser fileChooser;

	public ImportMusicAction(DatabaseManager databaseManager, ApplicationPreferences applicationPreferences)
	{
		super("Import Music");
		this.databaseManager = databaseManager;
		this.applicationPreferences = applicationPreferences;
		this.fileChooser = new DirectoryChooser();
		this.fileChooser.setTitle((String) getValue(Action.NAME));
		Path initPath = applicationPreferences.getMusicRootDirectory();
		if(initPath != null && Files.exists(initPath))
		{
			fileChooser.setInitialDirectory(initPath.getParent().toFile());
		}
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
			databaseManager.purgeLibrary();
			try
			{
				Files.walkFileTree(path, new AudioFileVisitor());
				databaseManager.commit();
				applicationPreferences.setMusicRootDirectory(path);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		});
	}
}
