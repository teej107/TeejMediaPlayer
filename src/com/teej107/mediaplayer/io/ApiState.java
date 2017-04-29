package com.teej107.mediaplayer.io;

import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by teej107 on 4/28/2017.
 */
public class ApiState extends AbstractJsonState
{
	private static final String GRACENOTE_API = "gracenote";
	public static final String GRACENOTE_API_USERID = "userID";

	protected ApiState(String rawData)
	{
		super(rawData);
	}

	public Map<String, Object> getGracenote()
	{
		Map<String, Object> gracenote = (Map<String, Object>) dataMap.get(GRACENOTE_API);
		if(gracenote == null)
		{
			gracenote = new JSONObject();
			dataMap.put(GRACENOTE_API, gracenote);
		}
		return gracenote;
	}
}
