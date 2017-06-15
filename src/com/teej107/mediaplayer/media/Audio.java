package com.teej107.mediaplayer.media;

import org.jaudiotagger.audio.SupportedFileFormat;

import java.nio.file.Path;
import java.util.*;

/**
 * Created by teej107 on 6/14/2017.
 */
public class Audio
{
	private static final Collection<String> SUPPORTED_FILE_FORMATS;

	public static boolean isSupported(Path path)
	{
		String fileStr = path.toString();
		int extIndex = fileStr.lastIndexOf('.');
		return extIndex != -1 && SUPPORTED_FILE_FORMATS.contains(fileStr.substring(extIndex + 1).toLowerCase());
	}

	static
	{
		Collection<String> fileFormats = new HashSet<>();
		for (SupportedFileFormat sff : SupportedFileFormat.values())
		{
			fileFormats.add(sff.getFilesuffix());
		}
		SUPPORTED_FILE_FORMATS = Collections.unmodifiableCollection(fileFormats);
	}
}
