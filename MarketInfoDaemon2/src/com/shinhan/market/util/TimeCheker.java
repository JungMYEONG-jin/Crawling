package com.shinhan.market.util;

import java.util.Calendar;

/**
 * @author parkyk
 * 시간 체킹을 위한 체커
 */
public class TimeCheker {
	/**
	 * HHmmSS
	 */
	private Calendar dateTime = null;
	
	public Calendar getDateTime() {
		return dateTime;
	}

	public TimeCheker(String checkTime) {
		super();
		
		//CheckTime HHmmSS
		
		if(checkTime.length() == 14){
			// yyyyMMddHHmmss
			try {
				int hour = Integer.parseInt(checkTime.substring(8,10));
				int min = Integer.parseInt(checkTime.substring(10,12));
				int sec = Integer.parseInt(checkTime.substring(12,14));
				
				Calendar cal = getCurrentCalendar();
				cal.set(Calendar.YEAR, Integer.parseInt(checkTime.substring(0,4)));
				cal.set(Calendar.MONTH, Integer.parseInt(checkTime.substring(4,6)));
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(checkTime.substring(6,8)));
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, min);
				cal.set(Calendar.SECOND, sec);
				
				dateTime = (Calendar) cal.clone();
				
			} catch (Exception e) {
				dateTime = null;
			}
			
		} else {
			try {
				int hour = Integer.parseInt(checkTime.substring(0,2));
				int min = Integer.parseInt(checkTime.substring(2,4));
				int sec = Integer.parseInt(checkTime.substring(4,6));
				
				Calendar cal = getCurrentCalendar();
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, min);
				cal.set(Calendar.SECOND, sec);
				
				dateTime = (Calendar) cal.clone();
				
			} catch (Exception e) {
				dateTime = null;
			}			
		}
	}
	
	/**
	 * 값이 정상적인지 체크한다.
	 */
	public boolean isVaildate(){
		if(dateTime == null){
			return false;
		}
	
		Calendar calCurrent = getCurrentCalendar();
		
		if(calCurrent.after(dateTime)){
			return false;
		}
		
		dateTime.toString();
		
		return true;
	}

	@Override
	public String toString() {
		if(dateTime != null){
			
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%04d",dateTime.get(Calendar.YEAR)));
			sb.append(String.format("%02d",dateTime.get(Calendar.MONTH)));
			sb.append(String.format("%02d",dateTime.get(Calendar.DAY_OF_MONTH)));
			sb.append(String.format("%02d",dateTime.get(Calendar.HOUR_OF_DAY)));
			sb.append(String.format("%02d",dateTime.get(Calendar.MINUTE)));
			sb.append(String.format("%02d",dateTime.get(Calendar.SECOND)));
			
			return sb.toString();
					
		} else {
			return super.toString();			
		}

	}
	
	
	private Calendar getCurrentCalendar() {
		Calendar calCurrent = Calendar.getInstance();
		calCurrent.setTimeInMillis(System.currentTimeMillis());
		return calCurrent;
	}
	
	/**
	 * 시간 경과했는지 체크.
	 */
	public boolean checkElapseTime() throws Exception { 
		
		if(dateTime != null){
			
			Calendar calCurrent = getCurrentCalendar();
			
			if(calCurrent.before(dateTime)){
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	
}
