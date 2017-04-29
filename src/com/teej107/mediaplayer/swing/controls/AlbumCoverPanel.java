package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.io.AlbumManager;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.SongChangeListener;
import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author teej107
 * @since Apr 10, 2015
 */
public class AlbumCoverPanel extends JPanel implements SongChangeListener
{
	private static final Image ICON;
	private ImagePane cover;
	private Path currentPath;
	private AlbumManager albumManager;

	private ExecutorService service = Executors.newCachedThreadPool();

	public AlbumCoverPanel(AudioPlayer audioPlayer, AlbumManager albumManager)
	{
		super(new BorderLayout());
		this.albumManager = albumManager;
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
		Image changeTo = image == null ? ICON : image;
		cover.setImage(changeTo);
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
		Path albumCoverPath = albumManager.getAlbumCoverPath(song);
		if(currentPath != null && currentPath.equals(albumCoverPath))
			return;

		currentPath = albumCoverPath;
		Image image = song.getArtwork();
		if(!Files.exists(albumCoverPath))
		{
			setImage(image);
		}
		if (image == null)
		{
			service.submit(() ->
			{
				Image i = albumManager.getAndSaveAlbumCover(song);
				SwingEDT.invoke(() -> setImage(i));
			});
		}
	}
}
