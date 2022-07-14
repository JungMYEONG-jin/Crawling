package com.shinhan.market.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class CrytoDataSource extends BasicDataSource {
	public Logger m_log = Logger.getLogger(getClass());
	
	
	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
		m_log.debug("setPassword START ==> "+ password);
		try {
			m_log.debug("AES256Cipher.AES_Decode(password)==> "+ AES256Cipher.AES_Decode(password));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			super.setPassword(AES256Cipher.AES_Decode(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String test = "qoa/OE1pduoVSyt3k8AvLQ==";
//		System.out.println(AES256Cipher.AES_Decode(test));
	}
}
