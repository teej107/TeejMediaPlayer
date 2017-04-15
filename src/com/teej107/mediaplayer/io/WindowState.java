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
public class WindowState
{
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String STATE = "state";

	private String rawData;
	private JSONParser jsonParser;
	private Map<String, Object> dataMap;

	/**
	 * @param rawData JSON format data
	 */
	protected WindowState(String rawData)
	{
		this.rawData = rawData;
		this.jsonParser = new JSONParser();
	}

	/**
	 * Loads the window state data
	 * @return a Response whether the method has successfully loaded the data or not
	 */
	protected Response load()
	{
		try
		{
			dataMap = (rawData == null ? new JSONObject() : (Map<String, Object>) jsonParser.parse(rawData));
			return Response.createOkResponse("WindowState loaded");
		}
		catch (ParseException e)
		{
			dataMap = new JSONObject();
			return Response.createErrorResponse("Failed to load data:", e.getMessage());
		}
	}

	/**
	 * Set the window size
	 * @param d Dimension object
	 */
	public void setWindowSize(Dimension d)
	{
		dataMap.put(WIDTH, d.width);
		dataMap.put(HEIGHT, d.height);
	}

	/**
	 * Get the window size
	 * @return Dimension object
	 */
	public Dimension getWindowSize()
	{
		return new Dimension(((Number) dataMap.getOrDefault(WIDTH, 800)).intValue(),
				((Number) dataMap.getOrDefault(HEIGHT, 600)).intValue());
	}

	/**
	 * Set the window location
	 * @param point Point object
	 */
	public void setLocation(Point point)
	{
		dataMap.put(X, point.x);
		dataMap.put(Y, point.y);
	}

	/**
	 * Get the window location
	 * @return Point object
	 */
	public Point getLocation()
	{
		return new Point(((Number) dataMap.getOrDefault(X, 0)).intValue(), ((Number) dataMap.getOrDefault(Y, 0)).intValue());
	}

	/**
	 * Set the window state ie: maximized
	 * @param state
	 */
	public void setWindowState(int state)
	{
		dataMap.put(STATE, state);
	}

	/**
	 * Set the window state ie: JFrame.MAXIMIZED_BOTH
	 * @return
	 */
	public int getWindowState()
	{
		return ((Number) dataMap.getOrDefault(STATE, JFrame.MAXIMIZED_BOTH)).intValue();
	}

	/**
	 * Get the raw JSON data
	 * @return JSON string
	 */
	@Override
	public String toString()
	{
		return dataMap.toString();
	}
}
