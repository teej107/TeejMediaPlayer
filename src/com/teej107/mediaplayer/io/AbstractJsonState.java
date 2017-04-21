package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.util.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * Created by teej107 on 4/20/17.
 */
public abstract class AbstractJsonState
{
	protected String rawData;
	protected JSONParser jsonParser;
	protected Map<String, Object> dataMap;

	/**
	 * @param rawData JSON format data
	 */
	protected AbstractJsonState(String rawData)
	{
		this.rawData = rawData;
		this.jsonParser = new JSONParser();
	}

	/**
	 * Loads the state data
	 *
	 * @return a Response whether the method has successfully loaded the data or not
	 */
	protected Response load()
	{
		try
		{
			dataMap = (rawData == null ? new JSONObject() : (Map<String, Object>) jsonParser.parse(rawData));
			return Response.createOkResponse(getClass().getSimpleName() + " loaded");
		}
		catch (ParseException e)
		{
			dataMap = new JSONObject();
			return Response.createErrorResponse("Failed to load data:", e.getMessage());
		}
	}

	@Override
	public String toString()
	{
		return dataMap.toString();
	}
}
