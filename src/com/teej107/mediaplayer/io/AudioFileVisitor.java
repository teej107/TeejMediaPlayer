package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.Application;
import org.jaudiotagger.audio.SupportedFileFormat;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by teej107 on 4/21/2017.
 */
public class AudioFileVisitor extends SimpleFileVisitor<Path>
{
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr)
	{
		String fileStr = file.toString();
		int extIndex = fileStr.lastIndexOf('.');
		try
		{
			if (extIndex != -1 && SupportedFileFormat.valueOf(fileStr.substring(extIndex + 1).toUpperCase()) != null)
			{
				if(!Application.instance().getDatabaseManager().addToLibrary(file))
				{
					System.err.println("Failed to add: " + fileStr);
				}
			}
		}
		catch (IllegalArgumentException e)
		{

		}
		return FileVisitResult.CONTINUE;
	}
}
