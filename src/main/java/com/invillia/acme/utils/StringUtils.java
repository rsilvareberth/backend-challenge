package com.invillia.acme.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtils {

	public static Date stringToSqlDate(String value, String format) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		java.util.Date date = sdf1.parse(value);
		return new java.sql.Date(date.getTime());
	}
}