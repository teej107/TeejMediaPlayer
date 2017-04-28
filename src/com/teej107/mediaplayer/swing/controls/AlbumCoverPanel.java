package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.SongChangeListener;
import com.teej107.mediaplayer.media.audio.ISong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author teej107
 * @since Apr 10, 2015
 */
public class AlbumCoverPanel extends JPanel implements SongChangeListener
{
	private static final Image ICON;
	private ImagePane cover;

	public AlbumCoverPanel(AudioPlayer audioPlayer)
	{
		super(new BorderLayout());
		cover = new ImagePane(ICON);

		add(cover, BorderLayout.CENTER);

		audioPlayer.addSongChangeListener(this);
	}

	public Image getImage()
	{
		return cover.getImage();
	}

	public void setImage(Image image)
	{
		cover.setImage(image == null ? ICON : image);
		cover.invalidate();
		cover.repaint();
	}

	static
	{
		Image img = null;
		try
		{
			img = ImageIO.read(AlbumCoverPanel.class.getResource("/assets/no-album-art.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		ICON = (img == null ? new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB) : img);
	}

	@Override
	public void onSongChange(ISong song)
	{
		setImage(song.getArtwork());
	}
}
