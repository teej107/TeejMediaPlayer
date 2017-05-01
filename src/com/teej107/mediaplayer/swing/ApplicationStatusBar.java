package com.teej107.mediaplayer.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/30/2017.
 */
public class ApplicationStatusBar extends JPanel
{
	private JProgressBar progressBar;
	private JLabel progressLabel;

	public ApplicationStatusBar()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setMaximumSize(new Dimension(10, 18));
		this.progressBar = new JProgressBar();
		this.progressLabel = new JLabel();
		this.progressBar.setVisible(false);
		this.progressBar.setMaximumSize(new Dimension(120, getMaximumSize().height));
		this.progressLabel.setVisible(false);
		add(progressBar);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(progressLabel);
	}

	public void setProgressVisible(boolean visible)
	{
		progressBar.setVisible(visible);
		progressLabel.setVisible(visible);
	}

	public JProgressBar getProgressBar()
	{
		return progressBar;
	}

	public JLabel getProgressLabel()
	{
		return progressLabel;
	}
}
