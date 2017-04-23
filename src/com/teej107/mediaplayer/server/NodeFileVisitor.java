package com.teej107.mediaplayer.server;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by teej107 on 4/22/17.
 */
public class NodeFileVisitor extends SimpleFileVisitor<Path>
{
	private Path root;
	private Path sourcePath;

	public NodeFileVisitor(Path root)
	{
		this.root = root;
		try
		{
			Files.createDirectories(root);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
	{
		if (sourcePath == null)
		{
			sourcePath = dir;
		}
		else
		{
			Files.createDirectories(root.resolve(sourcePath
					.relativize(dir)));
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	{
		Files.copy(file, root.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
		return FileVisitResult.CONTINUE;
	}
}
