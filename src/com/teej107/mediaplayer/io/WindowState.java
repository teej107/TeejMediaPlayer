package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.util.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Class for managing the state of the application window
 */
public class WindowState extends AbstractJsonState
{
	private static final Rectangle SCREEN = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String STATE = "state";

	/**
	 * @param rawData JSON format data
	 */
	protected WindowState(String rawData)
	{
		super(rawData);
	}

	/**
	 * Set the window size
	 *
	 * @param d Dimension object
	 */
	public void setWindowSize(Dimension d)
	{
		dataMap.put(WIDTH, d.width);
		dataMap.put(HEIGHT, d.height);
	}

	/**
	 * Get the window size
	 *
	 * @return Dimension object
	 */
	public Dimension getWindowSize()
	{
		return new Dimension(((Number) dataMap.getOrDefault(WIDTH, SCREEN.width / 1.5)).intValue(),
				((Number) dataMap.getOrDefault(HEIGHT, SCREEN.height / 1.3)).intValue());
	}

	/**
	 * Set the window location
	 *
	 * @param point Point object
	 */
	public void setLocation(Point point)
	{
		dataMap.put(X, point.x);
		dataMap.put(Y, point.y);
	}

	/**
	 * Get the window location
	 *
	 * @return Point object
	 */
	public Point getLocation()
	{
		return new Point(((Number) dataMap.getOrDefault(X, 20)).intValue(), ((Number) dataMap.getOrDefault(Y, 20)).intValue());
	}

	/**
	 * Set the window state ie: maximized
	 *
	 * @param state
	 */
	public void setWindowState(int state)
	{
		dataMap.put(STATE, state);
	}

	/**
	 * Set the window state ie: JFrame.MAXIMIZED_BOTH
	 *
	 * @return
	 */
	public int getWindowState()
	{
		return ((Number) dataMap.getOrDefault(STATE, JFrame.MAXIMIZED_BOTH)).intValue();
	}
}
