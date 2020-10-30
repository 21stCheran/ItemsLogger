package com.twentyonec.ItemsLogger.utils;

import java.util.regex.Pattern;

public class Regex {

	final private static String dateRegex = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
	final private static String timeRegex = "^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?$";
	final private static String indexRegex = "^[1-9]{1,3}$";
	final private static Pattern dateRegexPattern = Pattern.compile(dateRegex);
	final private static Pattern timeRegexPattern = Pattern.compile(timeRegex);
	final private static Pattern indexRegexPattern = Pattern.compile(indexRegex);

	public static boolean matchDate(final String date) {
		return dateRegexPattern.matcher(date).matches();
	}
	public static boolean matchTime(final String time) {
		return timeRegexPattern.matcher(time).matches();
	}
	public static boolean matchIndex(final String index) {
		return indexRegexPattern.matcher(index).matches();
	}

}
