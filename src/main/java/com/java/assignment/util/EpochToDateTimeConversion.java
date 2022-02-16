package com.java.assignment.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EpochToDateTimeConversion {

	public static String convertToDateTime(long epochSeconds) {

		Date date = new Date(epochSeconds);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getDefault());
		String formattedDate = sdf.format(date);

		return formattedDate;
	}

	public static long createTimestampFromLocalDateTime(LocalDateTime dateTime) {

		ZoneId zoneId = ZoneId.systemDefault();
		return dateTime.atZone(zoneId).toEpochSecond();
	}
}