package com.teej107.mediaplayer.server;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by teej107 on 5/7/2017.
 */
public class CopyToPathVisitor extends SimpleFileVisitor<Path>
{
	private Path to;

	public CopyToPathVisitor(Path to)
	{
		this.to = to;
		try
		{
			Files.createDirectories(to);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
	{
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	{
		Files.copy(file, to.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		return FileVisitResult.CONTINUE;
	}
}
