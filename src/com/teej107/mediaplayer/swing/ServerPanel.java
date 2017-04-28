package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.swing.action.ServerToggleAction;
import com.teej107.mediaplayer.swing.components.LabelTextfield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by teej107 on 4/26/2017.
 */
public class ServerPanel extends JPanel implements MouseListener
{
	private SpringLayout layout;

	private ApplicationPreferences applicationPreferences;
	private LabelTextfield serverPort;
	private JButton startServer, save;

	public ServerPanel(ApplicationPreferences applicationPreferences)
	{
		this.applicationPreferences = applicationPreferences;
		this.layout = new SpringLayout();
		setLayout(layout);

		this.serverPort = new LabelTextfield("Server Port");
		this.serverPort.getTextField().setText(Integer.toString(applicationPreferences.getServerPort()));
		layout.putConstraint(SpringLayout.WEST, serverPort, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, serverPort, 0, SpringLayout.NORTH, this);
		add(serverPort);

		this.startServer = new JButton(new ServerToggleAction());
		layout.putConstraint(SpringLayout.WEST, startServer, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, startServer, 0, SpringLayout.SOUTH, this);
		add(startServer);

		this.save = new JButton("Save");
		layout.putConstraint(SpringLayout.EAST, save, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, save, 0, SpringLayout.SOUTH, this);
		save.addMouseListener(this);
		add(save);

	}

	@Override
	public Insets getInsets()
	{
		return new Insets(10, 10, 10, 10);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		try
		{
			int port = Integer.parseInt(serverPort.getTextField().getText());
			applicationPreferences.setServerPort(port);
		}
		catch (NumberFormatException e1)
		{

		}
		SwingUtilities.getWindowAncestor(this).dispose();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
}
