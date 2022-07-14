package com.shinhan.market.daemon.dao;

import java.util.Calendar;

import com.shinhan.market.util.TimeCheker;

/**
 * @author parkyk
 * 설정 프로퍼티 관련 Class
 */
public class MarketPropertyDao {
	
	public static final String SETTING_Y="Y";
	public static final String SETTING_N="N";

	public static final String STATUS_NOT="0";
	public static final String STATUS_SUCCESS="1";
	public static final String STATUS_UPDATE="2";
	
	public static final String DATA_TYPE_STRING="0";
	public static final String DATA_TYPE_XML="1";
	public static final String DATA_TYPE_JSON="2";
	
	private String seq;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPropertyVersion() {
		return propertyVersion;
	}
	public void setPropertyVersion(String propertyVersion) {
		this.propertyVersion = propertyVersion;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	public String getPropertyDataType() {
		return propertyDataType;
	}
	public void setPropertyDataType(String propertyDataType) {
		this.propertyDataType = propertyDataType;
	}
	public String getPropertyData() {
		return propertyData;
	}
	public void setPropertyData(String propertyData) {
		this.propertyData = propertyData;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getUptDt() {
		return uptDt;
	}
	public void setUptDt(String uptDt) {
		this.uptDt = uptDt;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getIsSetting() {
		return isSetting;
	}
	public void setIsSetting(String isSetting) {
		this.isSetting = isSetting;
	}
	public String getEtc1() {
		return etc1;
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	public String getEtc3() {
		return etc3;
	}
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	private String propertyVersion;
	@Override
	public String toString() {
		return "MarketPropertyDao [seq=" + seq + ", propertyVersion="
				+ propertyVersion + ", propertyStatus=" + propertyStatus
				+ ", propertyDataType=" + propertyDataType + ", propertyData="
				+ propertyData + ", regDt=" + regDt + ", uptDt=" + uptDt
				+ ", regUserId=" + regUserId + ", isSetting=" + isSetting
				+ ", etc1=" + etc1 + ", etc2=" + etc2 + ", etc3=" + etc3 + "]";
	}
	private String propertyStatus;
	private String propertyDataType;
	private String propertyData;
	private String regDt;
	private String uptDt;
	private String regUserId;
	private String isSetting;
	private String etc1;
	private String etc2;
	private String etc3;
	
	public boolean isChangeValue(MarketPropertyDao newMarketPropertyDao){
		if(newMarketPropertyDao == null){
			return false;
		}
		
		System.out.println("1111");
		int newVer = getVersionToInt(newMarketPropertyDao.getPropertyVersion());
		int ver = getVersionToInt(getPropertyVersion());
		if(newVer > ver){
			return true;
		}

		System.out.println("22222");
		// 등록시간 체크
		TimeCheker timeCheckerNewRegDt = new TimeCheker(newMarketPropertyDao.getRegDt());
		TimeCheker timeCheckerRegDt = new TimeCheker(getRegDt());
		
		System.out.println("333");
		if(timeCheckerNewRegDt.getDateTime().after(timeCheckerRegDt.getDateTime())){
			return true;
		} 
		
		System.out.println("4444 ");
		TimeCheker timeCheckerNewUptDt = new TimeCheker(newMarketPropertyDao.getUptDt());
		TimeCheker timeCheckerUptDt = new TimeCheker(getUptDt());
		if(timeCheckerNewUptDt.getDateTime().after(timeCheckerUptDt.getDateTime())){
			return true;
		}
		System.out.println("55555 ");
		return false;
	}
	private int getVersionToInt(String version) {
		int ret = 0;
		try {
			ret = Integer.parseInt(version.replace(".", ""));
		} catch (Exception e) {
		}
		return ret;
	}

//	private double getDouble(String str){
//		double ret = 0;
//		try {
//			ret = Double.parseDouble(str);
//		} catch (Exception e) {
//		}
//		return ret;
//	}
}
