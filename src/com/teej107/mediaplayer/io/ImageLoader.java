package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by teej107 on 5/6/2017.
 */
public class ImageLoader
{
	private static final ImageLoader IMAGE_MANAGER = new ImageLoader();

	public static ImageLoader getInstance()
	{
		return IMAGE_MANAGER;
	}

	private Image getImage(String path)
	{
		try
		{
			return ImageIO.read(getClass().getResource("/assets" + path + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public Image getPlayButton(boolean pressed)
	{
		return getImage("/button/" + (pressed ? "play press" : "play"));
	}

	public Image getPauseButton(boolean pressed)
	{
		return getImage("/button/" + (pressed ? "pause press" : "pause"));
	}

	public Image getNextButton(boolean pressed)
	{
		return getImage("/button/" + (pressed ? "next press" : "next"));
	}

	public Image getPreviousButton(boolean pressed)
	{
		return ImageUtil.flip(getNextButton(pressed), -1, 1);
	}
}
