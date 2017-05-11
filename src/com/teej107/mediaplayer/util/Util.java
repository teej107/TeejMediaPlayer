package com.teej107.mediaplayer.util;

import com.sun.istack.internal.Nullable;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.media.audio.ISong;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by teej107 on 4/23/2017.
 */
public class Util
{
	private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

	public static String getRandomAlphaNumeric(int length)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			sb.append(ALPHA_NUMERIC.charAt(ThreadLocalRandom.current().nextInt(0, ALPHA_NUMERIC.length())));
		}
		return sb.toString();
	}

	public static String toJSON(ISong song, @Nullable String urlPath)
	{
		return toJSONObject(song, urlPath).toString();
	}

	public static String toJSON(ISong song)
	{
		return toJSON(song, null);
	}

	public static JSONObject toJSONObject(ISong song, @Nullable String urlPath)
	{
		Application app = Application.instance();
		if (urlPath == null)
		{
			Path songPath = Paths.get(song.getURI());
			urlPath = app.getApplicationPreferences().getMusicRootDirectory().relativize(songPath).toString();
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("artist", song.getArtist());
		jsonObject.put("title", song.getTitle());
		jsonObject.put("album", song.getAlbum());
		jsonObject.put("path", urlPath.replace("\\", "/"));

		String albumArt = app.getApplicationPreferences().getAlbumArtRootDirectory()
				.relativize(app.getAlbumManager().getAlbumCoverPath(song)).toString();
		jsonObject.put("album art", albumArt.replace("\\", "/"));
		jsonObject.put("year", song.getYear());
		jsonObject.put("genre", song.getGenre());
		jsonObject.put("duration", song.getDuration());
		//TODO: track no.
		return jsonObject;
	}

	public static JSONObject toJSONObject(ISong song)
	{
		return toJSONObject(song, null);
	}

	public static void setChildrenUnfocusable(JComponent component)
	{
		component.setFocusable(false);
		for (Component child : component.getComponents())
		{
			if (child instanceof JComponent && !(child instanceof JTextComponent))
			{
				setChildrenUnfocusable((JComponent) child);
			}
		}
	}
}
