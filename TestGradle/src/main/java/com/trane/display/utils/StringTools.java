package com.trane.display.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
	public static boolean isNull(String str) 
	{
		return (str == null ? true : false);
	}
	
	public static boolean isEmpty(String str) 
	{
		return (str.equals("") ? true : false);
	}

	public static boolean isNullOrEmpty(String str) 
	{
		return (isNull(str) || isEmpty(str));
	}

	public static String getMatch(String source, String rex) 
	{
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) 
		{
			return source.substring(matcher.start(), matcher.end());
		}
		return null;

	}

	public static String getMatchGroup(String source, String rex, int groupIndex) 
	{
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) 
		{
			return matcher.group(groupIndex);
		}
		return null;

	}

	public static void replaceAll(String source, String rex, String replaceBy) 
	{
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) 
		{
			matcher.replaceAll(replaceBy);
		}
	}

	public static boolean isMatch(String source, String rex) 
	{
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(source);
		return matcher.matches();
	}
}
