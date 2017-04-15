package com.teej107.mediaplayer.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * A basic class for handling responses
 */
public class Response
{
	public static int OK = 1;
	public static int ERROR = -1;

	private String[] message;
	private int status;

	public Response(int status, String... message)
	{
		this.message = message;
		this.status = status;
	}

	/**
	 * Get the message array
	 * @return message array
	 */
	public String[] getMessage()
	{
		return message;
	}

	/**
	 * Appends all the strings in the messages array into a single string.
	 * @return the messages as a string
	 */
	public String getMessageAsString()
	{
		StringBuilder sb = new StringBuilder();
		for(String s : message)
		{
			sb.append(s).append('\n');
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Get the status code of this response
	 * @return the status code
	 */
	public int getStatus()
	{
		return status;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(message) + status;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Response)
		{
			Response res = (Response) o;
			if(res.status != status)
				return false;

			return Arrays.equals(res.message, message);
		}
		return false;
	}

	@Override
	public String toString()
	{
		return getClass().getName() + "[" + "status=" + status + ", message=" + Arrays.toString(message) + "]";
	}

	/**
	 * Shortcut method for creating a response for when an action has failed to perform/complete
	 * @param message messages to use for the response
	 * @return the Response
	 */
	public static Response createErrorResponse(String... message)
	{
		return new Response(ERROR, message);
	}

	/**
	 * Shortcut method for creating a response for when an action is successful.
	 * @param message messages to use for the response
	 * @return the Response
	 */
	public static Response createOkResponse(String... message)
	{
		return new Response(OK, message);
	}
}
