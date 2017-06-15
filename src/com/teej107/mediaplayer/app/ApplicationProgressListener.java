package com.teej107.mediaplayer.app;

/**
 * Created by teej107 on 6/14/2017.
 */
public interface ApplicationProgressListener
{
	void onMinimumChange(int min);
	void onValueChange(int value);
	void onMaximumChange(int max);
	void onStatusChange(String status);
}
