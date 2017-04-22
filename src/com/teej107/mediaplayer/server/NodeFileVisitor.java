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
	public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
	{
		System.out.println(root.resolve(path));
		if (Files.isDirectory(path))
		{
			Files.createDirectories(root.resolve(path));
		}
		else
		{
			Files.copy(path, root.resolve(path), StandardCopyOption.REPLACE_EXISTING);
		}
		return FileVisitResult.CONTINUE;
	}
}
