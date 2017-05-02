package com.teej107.mediaplayer.server;

/**
 * Created by teej107 on 5/1/2017.
 */
public interface ServerStateListener
{
	void onStart();
	void onStop();
	void onInstalling();
	void onInstalled();
}
