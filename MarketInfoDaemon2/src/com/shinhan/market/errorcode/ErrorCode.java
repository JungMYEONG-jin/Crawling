package com.shinhan.market.errorcode;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.shinhan.market.crawling.Crawling;
import com.shinhan.market.util.UtilString;

public class ErrorCode {
	
	private final static String[][] errorCode = {

		{"A1000","PropertyInfo의 XML Data가 존재하지 않습니다."},
		{"A1001","XML파싱 초기화중 에러가 발생하였습니다."},
		{"A1002","CheckTime 데이터 파싱에 실패하였습니다."},
		{"A1003","(주기적) 마켓 정보 송신에 실패하였습니다. "},
		{"A1004","스레드 Sleep 처리중 에러가 발생했습니다."},
		{"A1005","패킷 보냄 여부 체크 중 에러가 발생하엿습니다."},
		{"A1006","마켓데몬 처리중 에러가 발생하였습니다."},
		{"A1007","설정정보 변경 려부 처리 중 에러가 발생하였습니다."},
		{"A1008","설정정보  -> 데이터 파싱중 에러가 발생하였습니다."},
		
		{"B1000","의도 하지 않는 곳에서의 Exception 발생으로 데몬 실행에 문제가 발생하였습니다. 원인을 확인해 주세요."},
		{"B1001","market 정보 처리중 에러가 발생했습니다."},
		{"B1002","Crawling원시 데이터를 가져오는 중 에러가 발생하였습니다."},
		{"B1003","발송 스레드 Sleep 처리중 에러가 발생했습니다"},
		/*
		 * Crawling관련 에러
		 * */
		{"C1000","CrawlingData의 값이 없습니다."},
		{"C1001","스토어 URL이 없습니다. 스토어 URL을 확인해주세요."},
		{"C1002","JSoup Document 파싱중 에러가 발생하였습니다."},
		{"C1003","Crawling중 에러가 발생하였습니다."},
		
		
		{"D1000","XML 생성중 오류가 발생하였습니다."},
		{"D1001","XML 파일 생성 중 오류가 발생 하였습니다."},

//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},		
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
//		{"",""},
	};

	private final static String ERROR_FORMAT = "[%s] %s";
	public static HashMap<String, String> errorCodeMap = new HashMap<String, String>();

	static {
		for(String[] errorCode : errorCode){
			if(UtilString.isNotEmpty(errorCode[0], errorCode[1])) {
				errorCodeMap.put(errorCode[0], errorCode[1]);				
			}
		}		
	}

	public static String LogInfo(Class<?> clazz, String code){
		try {
			String data = get(code);
//			Logger.getLogger(clazz).info(data);
			return data;
			
		} catch (Exception e) {
			return String.format(ERROR_FORMAT, "Code값이 존재하지 않습니다.", "Value값이 존재 하지 않습니다.");
			
		}
	}

	public static String LogError(Class<?> clazz, String code){
		try {
			String data = get(code);
			Logger.getLogger(clazz).error(data);
			return data;
			
		} catch (Exception e) {
			return String.format(ERROR_FORMAT, "Code값이 존재하지 않습니다.", "Value값이 존재 하지 않습니다.");
			
		}
	}
	
	public static String LogError(Class<?> clazz, String code, Exception e){
		try {
			String data = get(code);
			Logger.getLogger(clazz).error(data + " Exception : "+ e.getMessage());
			
			return data;
			
		} catch (Exception ex) {
			return String.format(ERROR_FORMAT, "Code값이 존재하지 않습니다.", "Value값이 존재 하지 않습니다. Exception : "+ ex.getMessage());
			
		}
	}

	public static String get(String code){
		try {
			if (UtilString.isNotEmpty(code)) {
				String val = errorCodeMap.get(code);
				if (UtilString.isNotEmpty(val)) {
					return String.format(ERROR_FORMAT, code, val);
				} else {
					return String.format(ERROR_FORMAT, code, "Value값이 존재 하지 않습니다."); // 벨류 없음. 
				}
			} else {
				return String.format(ERROR_FORMAT, "Code값이 존재하지 않습니다.", "Value값이 존재 하지 않습니다."); // 코드 이상
			}
		} catch (Exception e) {
			return String.format(ERROR_FORMAT, "Code값이 존재하지 않습니다.", "Value값이 존재 하지 않습니다.");
		}
	}
}
