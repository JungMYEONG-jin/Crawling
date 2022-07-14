package com.shinhan.market.util;

public class UtilString {
	public static boolean isNotEmpty(String...strings){
		if(strings == null){
			return false;
		}

		boolean isNotEmpty = true;
		for(String string : strings){
			if(string == null || string.length() <= 0){
				isNotEmpty = false;
				break;
			}
		}
		
		return isNotEmpty;
	}
}
