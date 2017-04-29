package com.teej107.mediaplayer.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author teej107
 * @since May 02, 2015
 */
public class ImageUtil
{
	public static Image getScaledInstance(Image img, int targetWidth,
			int targetHeight,
			Object hint, boolean higherQuality)
	{
		int w, h;
		if (higherQuality)
		{
			w = img.getWidth(null);
			h = img.getHeight(null);
		}
		else
		{
			w = targetWidth;
			h = targetHeight;
		}

		do
		{
			if (higherQuality && w > targetWidth)
			{
				w /= 2;
				if (w < targetWidth)
				{
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight)
			{
				h /= 2;
				if (h < targetHeight)
				{
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(img, 0, 0, w, h, null);
			g2.dispose();

			img = tmp;
		}
		while (w != targetWidth || h != targetHeight);

		return img;
	}

	public static Image resize(Image image, int width, int height, boolean higherQuality)
	{
		return getScaledInstance(image, width, height,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR, higherQuality);
	}

	public static Image resize(Image image, int width, int height)
	{
		return resize(image, width, height, true);
	}

	public static Image ratioWidth(Image image, int newWidth)
	{
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		int newHeight = (int) Math.ceil(((double) height * newWidth) / width);
		return resize(image, newWidth, newHeight);
	}

	public static Image ratioHeight(Image image, int newHeight)
	{
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		int newWidth = (int) Math.ceil(((double) width * newHeight) / height);
		return resize(image, newWidth, newHeight);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param image The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image image)
	{
		if (image instanceof BufferedImage)
			return (BufferedImage) image;

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return bufferedImage;
	}
}
