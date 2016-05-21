package com.cyw.office.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static  String format(Date date)throws Exception{
		if(date !=null){
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			return fmt.format(date);
		}
		return "";
	}
}
