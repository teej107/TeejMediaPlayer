package com.teej107.mediaplayer.io;

import org.jaudiotagger.audio.AudioFile;

import java.nio.file.Path;

/**
 * Created by teej107 on 6/13/2017.
 */
public interface ITagFetcher<T>
{
	T getData(Path path, AudioFile af);
}
