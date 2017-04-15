package com.teej107.mediaplayer;

import com.sun.javafx.application.PlatformImpl;

/**
 * Created by teej107 on 4/14/2017.
 */
public class Main
{
	public static void main(String[] args)
	{
		PlatformImpl.startup(() -> {});
		new Application();
	}
}
