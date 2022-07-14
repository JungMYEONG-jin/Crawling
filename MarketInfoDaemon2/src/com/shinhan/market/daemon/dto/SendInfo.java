package com.shinhan.market.daemon.dto;

import java.util.HashMap;

import com.shinhan.market.crawling.data.CrawlingData;


/**
 * @author parkyk
 * 발송정보 Info Class
 */
public class SendInfo implements CrawlingData {
	
	public static final String SEND_RESULT_ERROR="0";
	public static final String SEND_RESULT_OK="1";
	public static final String SEND_RESULT_CRAWLING_FAIL="2";
	
	public static String OS_TYPE_AND = "0";
	public static String OS_TYPE_IOS = "1";

	public static String METHOD_TYPE_GET = "1";
	public static String METHOD_TYPE_POST = "2";
	
	private String seq;	
	@Override
	public String toString() {
		return "SendInfo [seq=" + seq + ", appId=" + appId + ", sendStatus="
				+ sendStatus + ", errorMsg=" + errorMsg + ", userId=" + userId
				+ ", regDt=" + regDt + ", appPkg=" + appPkg + ", osType="
				+ osType + ", storeUrl=" + storeUrl + ", titleNode="
				+ titleNode + ", versionNode=" + versionNode + ", updateNode="
				+ updateNode + ", uptDt=" + uptDt + ", methodType="
				+ methodType + "]";
	}

	private String appId;
	private String sendStatus;
	private String errorMsg;
	private String userId;
	private String regDt;
	private String appPkg;
	private String osType;
	private String storeUrl;
	private String titleNode;
	private String versionNode;
	private String updateNode;
	private String uptDt;
	private String methodType;

	public SendInfo() {
	}
	
	public SendInfo(String seq, String appId, String appPkg, String osType, String storeUrl, String titleNode, String versionNode, String updateNode, String regDt, String uptDt, String methodType) {
		this.seq = seq;
		this.appId = appId;
		this.appPkg = appPkg;
		this.osType = osType;
		this.storeUrl = storeUrl;
		this.titleNode = titleNode;
		this.versionNode = versionNode;
		this.updateNode = updateNode;
		this.regDt = regDt;
		this.uptDt = uptDt;
		this.setMethodType(methodType);
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getReqUserId() {
		return userId;
	}
	public void setReqUserId(String reqUserId) {
		this.userId = reqUserId;
	}
	public String getAppPkg() {
		return appPkg;
	}

	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public String getTitleNode() {
		return titleNode;
	}

	public void setTitleNode(String titleNode) {
		this.titleNode = titleNode;
	}

	public String getVersionNode() {
		return versionNode;
	}

	public void setVersionNode(String versionNode) {
		this.versionNode = versionNode;
	}

	public String getUpdateNode() {
		return updateNode;
	}

	public void setUpdateNode(String updateNode) {
		this.updateNode = updateNode;
	}

	public String getUptDt() {
		return uptDt;
	}

	public void setUptDt(String uptDt) {
		this.uptDt = uptDt;
	}	
	
	public HashMap<String, String> toInsertMap(){
		HashMap<String, String> mapRet = new HashMap<String, String>();
		
		if(appId != null){
			mapRet.put("APP_ID", appId);
		}

		if(sendStatus != null){
			mapRet.put("SEND_STATUS", sendStatus);
		}
		
		if(errorMsg != null){
			mapRet.put("ERROR_MSG", errorMsg);
		} else {
			mapRet.put("ERROR_MSG", " ");
		}

		if(userId != null){
			mapRet.put("REQ_USER_ID", userId);
		}
		
		return mapRet;
		
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
}
