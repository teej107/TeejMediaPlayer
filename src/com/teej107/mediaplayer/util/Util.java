package com.teej107.mediaplayer.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by teej107 on 4/23/2017.
 */
public class Util
{
	private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

	public static String getRandomAlphaNumeric(int length)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			sb.append(ALPHA_NUMERIC.charAt(ThreadLocalRandom.current().nextInt(0, ALPHA_NUMERIC.length())));
		}
		return sb.toString();
	}
}
