package com.teej107.mediaplayer.swing.action;

import com.sun.javafx.application.PlatformImpl;
import com.teej107.mediaplayer.app.ApplicationProgress;
import com.teej107.mediaplayer.io.*;
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
	private ApplicationProgress applicationProgress;
	private DirectoryChooser fileChooser;

	public ImportMusicAction(DatabaseManager databaseManager, ApplicationPreferences applicationPreferences,
			ApplicationProgress applicationProgress)
	{
		super("Import Music");
		this.databaseManager = databaseManager;
		this.applicationPreferences = applicationPreferences;
		this.applicationProgress = applicationProgress;
		this.fileChooser = new DirectoryChooser();
		this.fileChooser.setTitle((String) getValue(Action.NAME));
		Path initPath = applicationPreferences.getMusicRootDirectory();
		if (initPath != null && Files.exists(initPath))
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
				applicationProgress.setValue(0);
				applicationProgress.setStatus("Importing music...");
				CounterFileVisitor counter = new CounterFileVisitor().walk(path);
				applicationProgress.setMax(counter.getCount());
				Files.walkFileTree(path, new AudioFileVisitor(databaseManager, (min, value, max) ->
				{
					applicationProgress.setValue(value);
					applicationProgress.setStatus(value + "/" + counter.getCount());
				}));
				databaseManager.commit();
				applicationPreferences.setMusicRootDirectory(path);
				applicationProgress.setStatus(null);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		});
	}
}
