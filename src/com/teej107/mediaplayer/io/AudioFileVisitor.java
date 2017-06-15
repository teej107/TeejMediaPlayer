package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.io.db.DatabaseManager;
import com.teej107.mediaplayer.media.Audio;
import com.teej107.mediaplayer.util.ProgressListener;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by teej107 on 4/21/2017.
 */
public class AudioFileVisitor extends SimpleFileVisitor<Path>
{


	private int count;
	private ProgressListener listener;
	private DatabaseManager databaseManager;

	public AudioFileVisitor(DatabaseManager databaseManager, ProgressListener listener)
	{
		this.count = 0;
		this.listener = listener;
		this.databaseManager = databaseManager;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
	{
		if (Audio.isSupported(file))
		{
			if (!databaseManager.addToLibrary(file))
			{
				System.err.println("Failed to add: " + file);
			}
		}
		listener.onProgressChange(0, ++count, count);
		return FileVisitResult.CONTINUE;
	}
}
