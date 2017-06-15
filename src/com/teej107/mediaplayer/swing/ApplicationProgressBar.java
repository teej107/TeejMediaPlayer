package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.app.ApplicationProgressListener;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/30/2017.
 */
public class ApplicationProgressBar extends JPanel implements ApplicationProgressListener
{
	private SpringLayout layout;
	private JProgressBar progressBar;
	private JLabel progressLabel;

	public ApplicationProgressBar()
	{
		this.layout = new SpringLayout();
		setLayout(layout);
		setMaximumSize(new Dimension(10, 18));
		setPreferredSize(getMaximumSize());
		this.progressBar = new JProgressBar();
		this.progressLabel = new JLabel();
		this.progressBar.setVisible(false);
		this.progressLabel.setVisible(false);

		layout.putConstraint(SpringLayout.WEST, progressBar, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, progressBar, 120, SpringLayout.WEST, progressBar);
		layout.putConstraint(SpringLayout.WEST, progressLabel, 5, SpringLayout.EAST, progressBar);

		for (Component c : getComponents())
		{
			height(c);
		}

		add(progressBar);
		add(progressLabel);
	}

	private void height(Component component)
	{
		layout.putConstraint(SpringLayout.NORTH, component, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, component, 0, SpringLayout.SOUTH, this);
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

	@Override
	public void onMinimumChange(int min)
	{
		SwingEDT.invoke(() -> getProgressBar().setMinimum(min));

	}

	@Override
	public void onValueChange(int value)
	{
		SwingEDT.invoke(() -> getProgressBar().setValue(value));
	}

	@Override
	public void onMaximumChange(int max)
	{
		SwingEDT.invoke(() -> getProgressBar().setMaximum(max));
	}

	@Override
	public void onStatusChange(String status)
	{
		SwingEDT.invoke(() ->
		{
			setProgressVisible(status != null);
			getProgressLabel().setText(status);
		});
	}
}
