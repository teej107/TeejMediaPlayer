package com.teej107.mediaplayer.media.audio;

import java.util.Map;

/**
 * Created by teej107 on 6/14/2017.
 */
public interface ISongJSONMapper
{
	void map(ISong song, Map<String, Object> map);
}
