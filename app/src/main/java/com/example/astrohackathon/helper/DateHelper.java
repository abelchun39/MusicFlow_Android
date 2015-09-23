package com.example.astrohackathon.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {
	public final static String myDateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static Date addDate(String nDate, int nDay){
		return addDate(parseStringToDate(nDate), nDay);
	}
	
	public static Date addDate(Date nDate, int nDay){
		Calendar c = new GregorianCalendar();
		c.setTime(nDate);
		c.add(Calendar.DATE, 30);
		Date d=c.getTime();
		
		return d;
	}
	
	public static String parseDateToString(Date nDate){
		return parseDateToString(nDate, myDateFormat);
	}
	
	public static String parseDateToString(Date nDate, String nFormat){
		if(nDate != null && nFormat != null){
			DateFormat formatter = new SimpleDateFormat(nFormat);
			
			return formatter.format(nDate);
		}
		
		return null;
	}
	
	public static Date parseStringToDate(String nDate){
		return parseStringToDate(nDate, myDateFormat);
	}
	
	public static Date parseStringToDate(String nDate, String nFormat){
		Date date = null;
		
		try{
			date = new SimpleDateFormat(nFormat
					, Locale.ENGLISH).parse(nDate);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		return date;
	}
	
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
}
