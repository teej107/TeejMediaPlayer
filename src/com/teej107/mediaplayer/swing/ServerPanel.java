package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.swing.action.ServerToggleAction;
import com.teej107.mediaplayer.swing.components.LabelTextfield;
import com.teej107.mediaplayer.swing.listener.InstallServerMouseListener;
import com.teej107.mediaplayer.util.SwingEDT;
import com.teej107.mediaplayer.util.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by teej107 on 4/26/2017.
 */
public class ServerPanel extends JPanel implements MouseListener, DocumentListener
{
	private final static String LOCAL_ADDRESS;
	private SpringLayout layout;

	private TeejMediaServer mediaServer;
	private ApplicationPreferences applicationPreferences;
	private LabelTextfield serverPort;
	private JButton startServer, save, install;
	private JLabel embeddedVersion, installedVersion, openInBrowser;

	public ServerPanel(ApplicationPreferences applicationPreferences, TeejMediaServer mediaServer, ExecutorService service)
	{
		this.applicationPreferences = applicationPreferences;
		this.mediaServer = mediaServer;
		this.layout = new SpringLayout();
		setLayout(layout);

		this.serverPort = new LabelTextfield("Server Port");
		this.serverPort.getTextField().setText(Integer.toString(applicationPreferences.getServerPort()));
		this.serverPort.getTextField().getDocument().addDocumentListener(this);
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
		layout.putConstraint(SpringLayout.EAST, install, 140, SpringLayout.WEST, install);
		install.addMouseListener(new InstallServerMouseListener(install, this, mediaServer));
		add(install);

		this.startServer = new JButton(new ServerToggleAction(applicationPreferences, mediaServer, serverPort.getTextField()));
		layout.putConstraint(SpringLayout.WEST, startServer, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, startServer, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, startServer, 140, SpringLayout.WEST, startServer);
		add(startServer);

		this.openInBrowser = new JLabel();
		this.openInBrowser.setForeground(Color.BLUE);
		layout.putConstraint(SpringLayout.WEST, openInBrowser, 2, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, openInBrowser, -5, SpringLayout.NORTH, startServer);
		add(openInBrowser);
		openInBrowser.addMouseListener(this);

		this.save = new JButton("Save");
		layout.putConstraint(SpringLayout.EAST, save, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, save, 0, SpringLayout.SOUTH, this);
		save.addMouseListener(this);
		add(save);

		update();
		Util.setChildrenUnfocusable(this);
	}

	public void update()
	{
		embeddedVersion.setText("Embedded Version: " + mediaServer.getEmbeddedVersion());
		installedVersion.setText("Installed Version: " + mediaServer.getInstalledVersion());
		openInBrowser.setText("http://" + LOCAL_ADDRESS + ":" + serverPort.getTextField().getText() + "/");
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
		if (e.getSource().equals(save))
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
		else if (e.getSource().equals(openInBrowser))
		{
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE))
			{
				try
				{
					if(!mediaServer.isRunning())
					{
						mediaServer.start();
					}
					desktop.browse(new URI(openInBrowser.getText()));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				catch (URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Opening in browser is unsupported", "Unsupported", JOptionPane.WARNING_MESSAGE);
			}
		}
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
		if (e.getSource().equals(openInBrowser))
		{
			Map attr = openInBrowser.getFont().getAttributes();
			attr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			openInBrowser.setFont(openInBrowser.getFont().deriveFont(attr));
		}
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if (e.getSource().equals(openInBrowser))
		{
			Map attr = openInBrowser.getFont().getAttributes();
			attr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE);
			openInBrowser.setFont(openInBrowser.getFont().deriveFont(attr));
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		SwingEDT.invoke(() -> update());
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		SwingEDT.invoke(() -> update());
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		SwingEDT.invoke(() -> update());
	}

	static
	{
		String address;
		try
		{
			InetAddress inetAddress = InetAddress.getLocalHost();
			address = inetAddress.getCanonicalHostName();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			address = "http://localhost";
		}
		LOCAL_ADDRESS = address;
	}
}
