package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.util.ProgressListener;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by teej107 on 4/30/2017.
 */
public class CounterFileVisitor extends SimpleFileVisitor<Path>
{
	private int count;
	private ProgressListener listener;

	public CounterFileVisitor(ProgressListener listener)
	{
		this.listener = listener;
	}

	public CounterFileVisitor()
	{

	}

	public CounterFileVisitor walk(Path start) throws IOException
	{
		count = 0;
		Files.walkFileTree(start, this);
		return this;
	}

	public int getCount()
	{
		return count;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
	{
		return super.preVisitDirectory(dir, attrs);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	{
		count++;
		if(listener != null)
		{
			listener.onProgressChange(0, count, count);
		}
		return super.visitFile(file, attrs);
	}
}
