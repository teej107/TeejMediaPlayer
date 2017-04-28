package com.teej107.mediaplayer.swing.action;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 4/26/2017.
 */
public class ExitAction extends AbstractAction
{
	public ExitAction()
	{
		super(Platform.getPlatform().getTerminate());
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Application.instance().exit();
	}
}
