package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.uninstaller.AbstractUninstaller;
import com.teej107.mediaplayer.io.uninstaller.FileUninstaller;

import javax.swing.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by teej107 on 5/31/17.
 */
public class UninstallPanel extends JPanel
{
	private List<AbstractUninstaller> uninstallers;

	public UninstallPanel(Application application)
	{
		this.uninstallers = new ArrayList<>();
		this.uninstallers.add(new FileUninstaller(application, application.getApplicationPreferences().getAlbumArtRootDirectory(),
				"Clear Album Cache"));
	}
}
