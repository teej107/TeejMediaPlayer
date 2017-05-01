package com.teej107.mediaplayer.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author teej107
 * @since May 02, 2015
 */
public class ImageUtil
{
	private static BufferedImage flip(BufferedImage image, double h, double v)
	{
		AffineTransform tx = AffineTransform.getScaleInstance(h, v);
		tx.translate(h * image.getWidth(), v * image.getHeight() - image.getHeight());
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		return image;
	}

	public static BufferedImage flip(Image image, double h, double v)
	{
		return flip(toBufferedImage(image), h, v);
	}

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

	public static Image loadImageResource(String asset, Dimension preferredDimension) throws IOException
	{
		Image image = ImageIO.read(ImageUtil.class.getResource(asset));
		if (preferredDimension.width > 0 && preferredDimension.height > 0)
			return resize(image, preferredDimension.width, preferredDimension.height);
		else if (preferredDimension.width == 0 && preferredDimension.height > 0)
			return ratioHeight(image, preferredDimension.height);
		else if (preferredDimension.height == 0 && preferredDimension.width > 0)
			return ratioWidth(image, preferredDimension.width);

		throw new IllegalArgumentException(preferredDimension.toString());
	}

}
