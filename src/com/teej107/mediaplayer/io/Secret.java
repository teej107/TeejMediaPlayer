package com.teej107.mediaplayer.io;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

/**
 * Created by teej107 on 4/28/2017.
 */
public class Secret
{
	private static final String GRACENOTE_CLIENT = "gracenote-client";
	private static final String GRACENOTE_ID = "id";
	private static final String GRACENOTE_TAG = "tag";

	private static Secret secret;

	private Map<String, Object> data;

	private Secret()
	{
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/secret.json"))))
		{
			String s;
			while((s = br.readLine()) != null)
			{
				sb.append(s);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			this.data = (Map<String, Object>) new JSONParser().parse(sb.toString());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public static Secret instance()
	{
		if(secret == null)
		{
			secret = new Secret();
		}
		return secret;
	}

	public String getGracenoteClientID()
	{
		return (String) ((Map)(data.get(GRACENOTE_CLIENT))).get(GRACENOTE_ID);
	}

	public String getGracenoteClientTag()
	{
		return (String) ((Map)(data.get(GRACENOTE_CLIENT))).get(GRACENOTE_TAG);
	}
}
