package com.teej107.mediaplayer.util;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.db.Column;
import com.teej107.mediaplayer.media.audio.ISong;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

	public static JSONObject toJSONObject(ISong song)
	{
		JSONObject jsonObject = new JSONObject();
		for (Column c : Column.values())
		{
			c.insertJSON(song, jsonObject);
		}
		Application app = Application.instance();
		String albumArt = app.getApplicationPreferences().getAlbumArtRootDirectory()
				.relativize(app.getAlbumManager().getAlbumCoverPath(song)).toString();
		jsonObject.put("album art", albumArt.replace("\\", "/"));
		return jsonObject;
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

	public static int toInt(String s, int def)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			return def;
		}
	}

	public static Response delete(Path path)
	{
		try
		{
			Files.delete(path);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return Response.createErrorResponse(e.getMessage());
		}
		return Response.createOkResponse();
	}

	public static String toString(Object[] array, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++)
		{
			sb.append(array[i]);
			if (delimiter != null && i + 1 < array.length)
			{
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	public static String repeat(String sequence, int times, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
		{
			sb.append(sequence);
			if (delimiter != null && i + 1 < times)
			{
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	public static String toHexString(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
		{
			sb.append(Integer.toHexString(c));
		}
		return sb.toString();
	}

	public static String fromHexString(String hex)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2)
		{
			sb.append((char) Integer.parseInt(hex.substring(i, (i + 2)), 16));
		}
		return sb.toString();
	}
}
