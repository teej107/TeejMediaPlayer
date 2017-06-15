package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.swing.action.*;

import javax.swing.*;

/**
 * Created by teej107 on 4/21/2017.
 */
public class ApplicationMenu extends JMenuBar
{
	public ApplicationMenu()
	{
		Application app = Application.instance();
		add(new JMenuChain("File")
				.append(new ImportMusicAction(app.getDatabaseManager(), app.getApplicationPreferences(), app.getApplicationProgress()))
				.separator()
				.append(new ExitAction()));
		add(new JMenuChain("Edit")
				.append(new ShowServerDialogAction(app)));
	}

	class JMenuChain extends JMenu
	{
		public JMenuChain(String name)
		{
			super(name);
		}

		public JMenuChain append(Action action)
		{
			this.add(new JMenuItem(action));
			return this;
		}

		public JMenuChain separator()
		{
			this.addSeparator();
			return this;
		}
	}
}
