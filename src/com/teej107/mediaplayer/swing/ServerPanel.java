package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.swing.action.ServerToggleAction;
import com.teej107.mediaplayer.swing.components.LabelTextfield;
import com.teej107.mediaplayer.swing.listener.InstallServerMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by teej107 on 4/26/2017.
 */
public class ServerPanel extends JPanel implements MouseListener
{
	private SpringLayout layout;

	private TeejMediaServer mediaServer;
	private ApplicationPreferences applicationPreferences;
	private LabelTextfield serverPort;
	private JButton startServer, save, install;
	private JLabel embeddedVersion, installedVersion;

	public ServerPanel(ApplicationPreferences applicationPreferences, TeejMediaServer mediaServer, ExecutorService service)
	{
		this.applicationPreferences = applicationPreferences;
		this.mediaServer = mediaServer;
		this.layout = new SpringLayout();
		setLayout(layout);

		this.serverPort = new LabelTextfield("Server Port");
		this.serverPort.getTextField().setText(Integer.toString(applicationPreferences.getServerPort()));
		layout.putConstraint(SpringLayout.WEST, serverPort, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, serverPort, 0, SpringLayout.NORTH, this);
		add(serverPort);

		this.installedVersion = new JLabel();
		layout.putConstraint(SpringLayout.WEST, installedVersion, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, installedVersion, 15, SpringLayout.SOUTH, serverPort);
		add(installedVersion);

		this.embeddedVersion = new JLabel();
		layout.putConstraint(SpringLayout.WEST, embeddedVersion, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, embeddedVersion, 10, SpringLayout.SOUTH, installedVersion);
		add(embeddedVersion);

		this.install = new JButton("Install");
		layout.putConstraint(SpringLayout.WEST, install, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, install, 10, SpringLayout.SOUTH, embeddedVersion);
		layout.putConstraint(SpringLayout.EAST, install, 100, SpringLayout.WEST, install);
		install.addMouseListener(new InstallServerMouseListener(install,this, mediaServer, service));
		add(install);

		this.startServer = new JButton(new ServerToggleAction(applicationPreferences ,mediaServer, serverPort.getTextField()));
		layout.putConstraint(SpringLayout.WEST, startServer, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, startServer, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, startServer, 100, SpringLayout.WEST, startServer);
		add(startServer);

		this.save = new JButton("Save");
		layout.putConstraint(SpringLayout.EAST, save, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, save, 0, SpringLayout.SOUTH, this);
		save.addMouseListener(this);
		add(save);

		update();
	}

	public void update()
	{
		embeddedVersion.setText("Embedded Version: " + mediaServer.getEmbeddedVersion());
		installedVersion.setText("Installed Version: " + mediaServer.getInstalledVersion());
		install.setEnabled(!mediaServer.isInstalled());
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