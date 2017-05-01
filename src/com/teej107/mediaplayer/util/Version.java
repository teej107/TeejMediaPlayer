package com.teej107.mediaplayer.util;

import com.sun.istack.internal.Nullable;

/**
 * Created by teej107 on 4/30/2017.
 */
public class Version
{
	private String major, minor, patch;

	public Version()
	{
		major = "0";
		minor = "0";
		patch = "0";
	}

	public Version(@Nullable String str)
	{
		this();
		if (str == null)
			return;

		String[] arr = str.split("\\.");
		if (arr.length > 0)
		{
			major = arr[0];
		}
		if (arr.length > 1)
		{
			minor = arr[1];
		}
		if (arr.length > 2)
		{
			patch = arr[2];
		}
	}

	public String getMajor()
	{
		return major;
	}

	public String getMinor()
	{
		return minor;
	}

	public String getPatch()
	{
		return patch;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Version version = (Version) o;

		if (!major.equals(version.major))
			return false;
		if (!minor.equals(version.minor))
			return false;
		return patch.equals(version.patch);
	}

	@Override
	public int hashCode()
	{
		int result = major.hashCode();
		result = 31 * result + minor.hashCode();
		result = 31 * result + patch.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return major + "." + minor + "." + patch;
	}
}
