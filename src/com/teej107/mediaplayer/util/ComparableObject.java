package com.teej107.mediaplayer.util;

import java.util.Objects;

/**
 * Objects that don't implement the Comparable interface can use this class
 */
public class ComparableObject<T> implements Comparable<Integer>
{
	private T obj;
	private int priority;

	/**
	 * @param obj Object that doesn't implement the comparable interface
	 * @param priority priority for the comparator
	 */
	public ComparableObject(T obj, int priority)
	{
		this.obj = obj;
		this.priority = priority;
	}

	/**
	 * @return The object passed in the constructor
	 */
	public T getObject()
	{
		return obj;
	}

	/**
	 * @return The priority passed in the constructor
	 */
	public int getPriority()
	{
		return priority;
	}

	@Override
	public int compareTo(Integer integer)
	{
		return priority - integer;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(obj, priority);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(obj instanceof ComparableObject)
		{
			ComparableObject cObj = (ComparableObject) obj;
			return this.obj.equals(cObj.getObject()) && priority == cObj.getPriority();
		}
		return false;
	}
}
