package com.shinhan.market.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author 김동헌
 * 
 *         이클래스는 [데몬/WAR 의 공통적으로 쓰일 유틸리티 메소드를 담은] 클래스 입니다.
 */
public class Util {
	/**
	 * SHA 암호화
	 * 
	 * @param inputText
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String makeSHA(String inputText)
			throws NoSuchAlgorithmException {
		String test = inputText;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(test.getBytes());
		byte[] digest = md.digest();

//		System.out.println(md.getAlgorithm());
//		System.out.println(digest.length);

		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
//			System.out.print(Integer.toHexString(b & 0xff) + "");
			sb.append(Integer.toHexString(b & 0xff));
		}

//		System.out.println("\n\nReturn String : " + sb.toString());
		return sb.toString();
	}

	/**
	 * 주어진 문자열에 대해 앞자리부터 몇자리까지만 보여줄 것인가.. ( aaaaaa -> aaa...)
	 * 
	 * @param s
	 *            변환할 문자
	 * @param len
	 *            보여줄 자릿수
	 * @param tails
	 *            자른 문자열 뒤 댓글
	 * @return 변환된 문자
	 */
	public static String fixString(String s, int len, String tails) {
		String retstr = null;
		int totlen = s.length();

		if (s == null || s.equals("")) {
			retstr = "";
		} else if (totlen > len) {
			retstr = s.substring(0, len);
			retstr += tails;
		} else {
			retstr = s;
		}

		return retstr;
	}

	/**
	 * 바이트 수만큼 문자열 자르기
	 * 
	 * @param str
	 *            자를 문자열
	 * @param sz
	 *            보여줄 자릿수
	 * @param tail
	 *            자른 문자열 뒤 보여지는 문자열
	 * @return 변환된 문자
	 */
	public static String getByteCut(String str, int sz, String tail) // throws
																		// UnsupportedEncodingException
	{
		String returnStr = "";
		StringCharacterIterator iter = null;
		if ((str.getBytes()).length > sz) {
			iter = new StringCharacterIterator(
					new String(str.getBytes(), 0, sz));
			int type = Character.getType(iter.last());
			if (type == Character.OTHER_SYMBOL)
				sz--;
			if (type == Character.UNASSIGNED)
				sz--;

			if (str.length() > sz) {
				// 재검사
				iter.setText(str.substring(0, sz));
				type = Character.getType(iter.last());
				if (type == Character.OTHER_SYMBOL)
					sz += 2;
			}

			// 문자를 다시 잘라 리턴
			returnStr = (new String(str.getBytes(), 0, sz)) + tail;
		} else {
			returnStr = str;
		}
		return returnStr;
	}

	/**
	 * 만약 한자리 날짜일경우 앞에 0을 붙여 리턴한다.
	 * 
	 * @param tempInt
	 *            월, 또는 일
	 * @return 두자리 월 또는 일
	 */
	public static String dateTwoConvert(int tempInt) {
		String s = null;
		if (tempInt < 10) {
			s = "0" + tempInt;
		} else {
			s = "" + tempInt;
		}
		return s;
	}

	/**
	 * 한글 변환
	 * 
	 * @param Break_String
	 *            한글로 변환할 String
	 * @return 한글로 변환된 String
	 */
	public static String toKor(String str) {
		String tmp = "";
		if (str == null || str.equals(""))
			return "";

		try {
			tmp = new String(str.getBytes("8859_1"), "KSC5601");
		} catch (Exception ex) {
			tmp = "";
		}
		return tmp;
	}

	public static String nullToBlank(String str) {
		if (str == null || str.equals(""))
			return "";
		return str;
	}

	public static String getStringFromObject(Object obj1) {
		if (obj1 == null)
			return "";
		return (String) obj1;
	}

	public static String nullToString(String str, String str2) {
		if (str == null || str.equals(""))
			return str2;
		return str;
	}

	public static String getSelectedStr(String str, String checkStr) {
		if (str.equals(checkStr)) {
			return "selected";
		} else {
			return "";
		}
	}
public static void main(String[] args) {
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSS");
	
	String currentTime = dateFormat.format(calendar.getTime());
//	System.out.println("현재시간==>"+currentTime);
//	System.out.println("현재시간==>"+currentTime.substring(0,6));
}
	/**
	 * Calendar 클래스를 YYYYMMDD 형식의 스트링으로 반환한다.
	 * 
	 * @param cal
	 *            Calendar 객체
	 * @return YYYYMMDD 형식의 스트링
	 */
	public static String getCalYYYYMMDD(Calendar cal) {
		String sDate = null;

		sDate = cal.get(Calendar.YEAR)
				+ Util.dateTwoConvert((cal.get(Calendar.MONTH)
						- Calendar.JANUARY + 1))
				+ Util.dateTwoConvert(cal.get(Calendar.DATE));

		return sDate;
	}
	
	/**
	 * Calendar 클래스를 YYYYMMDD 형식의 스트링으로 반환한다.
	 * 
	 * @param cal
	 *            Calendar 객체
	 * @return YYYYMMDD 형식의 스트링
	 */
	public static String getCalYYYY_MM_DD(Calendar cal) {
		String sDate = null;

		sDate = cal.get(Calendar.YEAR)
				+ "-"
				+ Util.dateTwoConvert((cal.get(Calendar.MONTH)
						- Calendar.JANUARY + 1)) + "-"
				+ Util.dateTwoConvert(cal.get(Calendar.DATE));

		return sDate;
	}

	/**
	 * 실행 컴퓨터 의 IP 가져오기 메소드
	 * 
	 * @return 서버IP
	 */
	public static String getServerIp() {
		String serverIp = null;

		InetAddress inetAddr;
		try {
			inetAddr = InetAddress.getLocalHost();
			serverIp = inetAddr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return serverIp;
	}

	/**
	 * 문자열의 길이와, 인자로 넘겨받은 한계숫자를 비교하여 작거나 같으면 true 를 리턴한다. DB TABLE 자릿수에 맞게
	 * Insert 하기 위한 메소드
	 * 
	 * @param string
	 *            검사할 String
	 * @param limit
	 *            검사할 String 의 최대 자리수
	 * @return 검사결과
	 */
	public static boolean tableLimitCheck(String string, int limit) {
		boolean bTrue = false;

		if (string.getBytes().length <= limit) {
			bTrue = true;
		}

		return bTrue;
	}

	/**
	 * YYYYMMDD 형식의 날짜를 년, 월, 일 로 분리한다.
	 * 
	 * @param day
	 *            YYYYMMDD 형식의 날짜
	 * @return 년, 월, 일 로 분리되어 저장된 String[]
	 */
	public static String[] dayDivide(String day) {
		String[] days = new String[3];

		days[0] = day.substring(0, 4);
		days[1] = day.substring(4, 6);
		days[2] = day.substring(6, 8);

		return days;
	}

	public static String[] dayDivide2(String day) {
		String[] days = new String[3];

		days[0] = day.substring(0, 4);
		days[1] = day.substring(5, 7);
		days[2] = day.substring(8, 10);

		return days;
	}

	public static String getStrYYYY_MM_DD(String day) {
		String days = "";

		days = day.substring(0, 4) + "-" + day.substring(4, 6) + "-"
				+ day.substring(6, 8);

		return days;
	}

	/**
	 * 2004/01/01 등의 날짜 형식중 원하는 년, 월, 일 중 하나를 가져올수 있는 메소드
	 * 
	 * @param date
	 * @param delimeter
	 *            구분자
	 * @param cnt
	 *            년/월/일 중 추출할 자릿수
	 * @return 년/월/일 중 추출된 문
	 */
	public static String dayDivide(String date, String delimeter, int cnt) {
		String day = null;
		String[] str = null;

		StringTokenizer st = new StringTokenizer(date, delimeter);

		if (st.countTokens() > 0) {
			str = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				str[i] = st.nextToken();
				i++;
			}
		}

		day = str[cnt];
		return day;
	}

	/**
	 * 분리하고 싶은 스트링을 받아 분리하여, 스트링 배열로 담는다.
	 * 
	 * @param string
	 *            분리할 스트링
	 * @param delimeter
	 *            분리 참조할 구분자
	 * @return 분리된 배열로 저장한 객체
	 */
	public static String[] stringDivide(String string, String delimeter) {
		String[] strings = null;

		StringTokenizer st = new StringTokenizer(string, delimeter);

		if (st.countTokens() > 0) {
			strings = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				strings[i] = st.nextToken();
				i++;
			}
		}

		return strings;
	}

	/**
	 * 넘겨받은 스트링이 영문인지, 한글인지 검사하는 메소드
	 * 
	 * @param string
	 *            검사할 문자
	 * @return 검사 결과
	 */
	public static boolean isEnglish(String string) {
		boolean result = true;

		for (int i = 0; i < string.length(); i++) {
			String ch = string.charAt(i) + "";
			if (ch.getBytes().length == 2) {
				result = false;
				break;
			}
		}

		return result;
	}

	public static String printStackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString());
		sb.append("\n");
		StackTraceElement element[] = e.getStackTrace();
		for (int idx = 0; idx < element.length; idx++) {
			sb.append("\tat ");
			sb.append(element[idx].toString());
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * YYYYMMDD 형식의 스트링을 Calendar 객체로 바꾼다.
	 * 
	 * @param sDate
	 * @return Calendar
	 */
	public static Calendar getStrCalendar(String sDate) {
		Calendar date = Calendar.getInstance();
		String setDate[] = Util.dayDivide(sDate);
		date.set(Integer.parseInt(setDate[0]),
				Integer.parseInt(setDate[1]) - 1, Integer.parseInt(setDate[2]),
				0, 0, 0);

		return date;
	}

	public static Calendar getStrCalendar2(String sDate) {
		Calendar date = Calendar.getInstance();
		String setDate[] = Util.dayDivide2(sDate);
		date.set(Integer.parseInt(setDate[0]),
				Integer.parseInt(setDate[1]) - 1, Integer.parseInt(setDate[2]),
				0, 0, 0);

		return date;
	}

	public static String getCalYYYYMMDDhhmmss(Calendar cal) throws Exception {
		String sDate = null;
		// sDate = cal.get(Calendar.YEAR) + "" +
		// dateTwoConvert((cal.get(Calendar.MONTH) - Calendar.JANUARY+1)) + "" +
		// dateTwoConvert(cal.get(Calendar.DATE)) + "" +
		// dateTwoConvert(cal.get(Calendar.HOUR)) + "" +
		// dateTwoConvert(cal.get(Calendar.MINUTE)) + "" +
		// dateTwoConvert(cal.get(Calendar.SECOND));
		sDate = cal.get(Calendar.YEAR)
				+ ""
				+ dateTwoConvert((cal.get(Calendar.MONTH) - Calendar.JANUARY + 1))
				+ "" + dateTwoConvert(cal.get(Calendar.DATE)) + ""
				+ dateTwoConvert(cal.get(Calendar.HOUR_OF_DAY)) + ""
				+ dateTwoConvert(cal.get(Calendar.MINUTE)) + ""
				+ dateTwoConvert(cal.get(Calendar.SECOND));
		// System.out.println("sDate ["+sDate+"]");

		return sDate;
	}

	public static String getCalhhmmss(Calendar cal) throws Exception {
		String sDate = null;
		// sDate = cal.get(Calendar.YEAR) + "" +
		// dateTwoConvert((cal.get(Calendar.MONTH) - Calendar.JANUARY+1)) + "" +
		// dateTwoConvert(cal.get(Calendar.DATE)) + "" +
		// dateTwoConvert(cal.get(Calendar.HOUR)) + "" +
		// dateTwoConvert(cal.get(Calendar.MINUTE)) + "" +
		// dateTwoConvert(cal.get(Calendar.SECOND));
		sDate = dateTwoConvert(cal.get(Calendar.HOUR_OF_DAY)) + ""
				+ dateTwoConvert(cal.get(Calendar.MINUTE)) + ""
				+ dateTwoConvert(cal.get(Calendar.SECOND));
		// System.out.println("sDate ["+sDate+"]");

		return sDate;
	}

	public static String getCalYYYY_MM_DD_hh_mm_ss(Calendar cal)
			throws Exception {
		String sDate = null;
		// sDate = cal.get(Calendar.YEAR) + "" +
		// dateTwoConvert((cal.get(Calendar.MONTH) - Calendar.JANUARY+1)) + "" +
		// dateTwoConvert(cal.get(Calendar.DATE)) + "" +
		// dateTwoConvert(cal.get(Calendar.HOUR)) + "" +
		// dateTwoConvert(cal.get(Calendar.MINUTE)) + "" +
		// dateTwoConvert(cal.get(Calendar.SECOND));
		sDate = cal.get(Calendar.YEAR)
				+ "-"
				+ dateTwoConvert((cal.get(Calendar.MONTH) - Calendar.JANUARY + 1))
				+ "-" + dateTwoConvert(cal.get(Calendar.DATE)) + " "
				+ dateTwoConvert(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ dateTwoConvert(cal.get(Calendar.MINUTE)) + ":"
				+ dateTwoConvert(cal.get(Calendar.SECOND));
		// System.out.println("sDate ["+sDate+"]");

		return sDate;
	}

	/**
	 * 인자로 받은 스트링의, i 값을 더해 리턴한다.
	 * 
	 * @param startDate
	 * @return 다음일
	 */
	public static String getDayMove(String startDate, int i) {
		Calendar date = Util.getStrCalendar(startDate);
		date.add(Calendar.DATE, i);

		return Util.getCalYYYYMMDD(date);
	}

	public static String getLastDay(String startDate) {
		String[] s_strings = Util.stringDivide(startDate, "-");
		Calendar static_cal = Util.getStrCalendar(s_strings[0] + s_strings[1]
				+ s_strings[2]);
		Calendar dynamic_cal = Util.getStrCalendar(s_strings[0] + s_strings[1]
				+ s_strings[2]);

		int cnt = 0;
		while (true) {
			if (!Util.dateTwoConvert(
					(dynamic_cal.get(Calendar.MONTH) - Calendar.JANUARY + 1))
					.equals(Util.dateTwoConvert((static_cal.get(Calendar.MONTH)
							- Calendar.JANUARY + 1)))) {
				break;
			}
			cnt++;
			// System.out.println(cnt+" "+Util.getCalYYYYMMDD(dynamic_cal));
			dynamic_cal.add(Calendar.DATE, 1);
		}

		return cnt + "";
	}

	/**
	 * 와일드 문자로 변환하여 리턴하기
	 * 
	 * @param string
	 *            원래 문자열
	 * @return 와일드 문자
	 */
	public static String getWildChar(String string) {
		String wild = "";

		for (int i = 0; i < string.length(); i++) {
			wild += "*";
		}

		return wild;
	}

	/**
	 * fileCopdy 메소드
	 * 
	 * @param mother
	 *            부모 파일
	 * @param clone
	 *            복사할 파일
	 * @return 실행 결과
	 */
	public static boolean fileCody(String mother, String clone) {
		boolean result = false;

		// File 객체를 프로그램 실행시의 인자를 이용하여 생성한다.
		// 예를 들어, java CopyFile test.txt output.txt
		// test.txt -> args[0]
		// output.txt -> args[1]
		File inputFile = new File(mother);
		File outputFile = new File(clone);

		// FileReader, FileWriter 클래스 객체를 생성
		try {
			FileReader in = new FileReader(inputFile);
			FileWriter out = new FileWriter(outputFile);

			int c;
			// FileReader 클래스 객체에서 파일의 끝까지 읽어서
			// FileWriter 클래스에 써준다.
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
			out.close();
			result = true;
		} catch (IOException e) {
			e.getMessage();
		} finally {
		}

		return result;
	}

	/**
	 * String을 int형으로 변환 (에러시 특정 int로 리턴)
	 * 
	 * @param s
	 *            int로 바꿀 문자열
	 * @param i
	 *            에러시 리턴값
	 * @return 변환된 int
	 */
	// String을 int형으로 변환
	// s(숫자형으로 바뀔 문자열),i(변환작없시 에러가 발생하면 기본값)
	// return(s가 바뀐 int)
	public static int toInteger(String s, int i) {
		int j;
		try {
			if (s == null || s == "")
				return i;
			j = Integer.parseInt(s);
		} catch (Exception exception) {
			j = i;
		}
		return j;
	}

	/**
	 * Enter키값을 html태그로 변환
	 * 
	 * @param s
	 *            변환할 문자
	 * @return 변환된 문자
	 */
	// Enter키값을 html태그로 변환
	public static String convLFtoBR(String s) {
		int i = s.length();
		String s1 = "";
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			if (c == '\r') {
				if (j < i - 1)
					if (s.charAt(j + 1) == '\n')
						j++;
				s1 = s1 + "<BR>";
			} else if (c == '\n') {
				if (j < i - 1)
					if (s.charAt(j + 1) == '\r')
						j++;
				s1 = s1 + "<BR>";

			} else {
				s1 = s1 + c;
			}

		}
		return s1;
	}

	/**
	 * Enter키값을 html태그로 변환
	 * 
	 * @param s
	 *            변환할 문자
	 * @return 변환된 문자
	 */
	// Enter키값을 html태그로 변환
	public static String convLFtoBR1(String s) {
		int i = s.length();
		String s1 = "";
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			if (c == '\r') {
				if (j < i - 1)
					if (s.charAt(j + 1) == '\n')
						j++;
				s1 = s1 + "<BR>";
			} else if (c == '\n') {
				if (j < i - 1)
					if (s.charAt(j + 1) == '\r')
						j++;
				s1 = s1 + "<BR>";

			} else if (c == ' ') {
				s1 = s1 + "&nbsp;";
			} else {
				s1 = s1 + c;
			}

		}
		return s1;
	}

	public static String cutString(String s, int c) {
		if (s.length() <= c) {
			return s;
		} else {
			return s.substring(0, c);
		}
	}

	public static String cutStringByte(String s, int cutlen) {
		if (s == null)
			return null; // return ""; 도 괜찮을 듯...

		byte[] ab = s.getBytes();
		int i, slen, cnt;

		slen = ab.length;

		if (slen <= cutlen)
			return s;

		cnt = 0;
		for (i = 0; i < cutlen; i++) {
			if ((((int) ab[i]) & 0xff) > 0x80)
				cnt++;
		}
		if ((cnt % 2) == 1)
			i--;

		return new String(ab, 0, i);
	}

	public static String cropByte(String str, int i, String trail) {
		if (str == null)
			return "";
		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		if (tmp.getBytes().length > i) {
			while (blen + 1 < i) {
				c = tmp.charAt(slen);
				blen++;
				slen++;
				if (c > 127)
					blen++; // 2-byte character..
			}
			tmp = tmp.substring(0, slen) + trail;
		}
		return tmp;
	}

	// MD5 hash값 구하기
	public static final String encodeMD5(String strInput) {
		String strOut = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(strInput.getBytes());
			byte[] hash = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String strHash = Integer.toHexString(0xff & (char) hash[i]);
				sb.append(strHash.length() == 1 ? "0" : "").append(strHash);
			}
			strOut = sb.toString();
		} catch (NoSuchAlgorithmException nsae) {
			strOut = strInput;
		}
		return strOut;
	}

	/**
	 * 특정기간 범위안에 있는지 체크 (예: 최근 5일안에 있나?)
	 * 
	 * @param strDate
	 *            날짜 (ex: 2004-06-19)
	 * @param sd
	 *            일수(범위날짜수)
	 * @return
	 */
	public static boolean isRecent(String strDate, int sd) {
		boolean boolResult = false;
		try {
			if (strDate != null && strDate.length() >= 10) {
				String yyyy = strDate.substring(0, 4);
				String mm = strDate.substring(5, 7);
				String dd = strDate.substring(8, 10);

				Calendar currentCal = Calendar.getInstance();
				Date currentDate = currentCal.getTime();

				int intYyyy = Integer.parseInt(yyyy);
				int intMm = Integer.parseInt(mm);
				int intDd = Integer.parseInt(dd);
				Calendar scal = Calendar.getInstance();
				scal.set(intYyyy, intMm - 1, intDd);
				Date sdate = scal.getTime();

				long millisDiff = currentDate.getTime() - sdate.getTime();
				int intResult = (int) (millisDiff / (1000 * 60 * 60 * 24));

				if (Math.abs(intResult) <= sd) {
					boolResult = true;
				}
			}

		} catch (Exception e) {
		}

		return boolResult;
	}
	
	public String getPushDataCut(String sendMsg, String key, int maxLength) throws Exception {
		
		String pushData = sendMsg;
		int totalMsgLength = 0;
		
		byte[] byteSendMsg = sendMsg.getBytes();
		int sendMsgLength = byteSendMsg.length;
		
		byte[] byteKey = key.getBytes();
		int keyLength = byteKey.length;
		
		totalMsgLength = sendMsgLength + keyLength;
		
		if (totalMsgLength > maxLength) {
			pushData = cropByte(sendMsg, maxLength - 2 - keyLength, "..");
		}
		return pushData;
	}	
}
