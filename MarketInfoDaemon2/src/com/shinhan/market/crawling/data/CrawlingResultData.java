package com.shinhan.market.crawling.data;

public class CrawlingResultData {
	private String appId;
	private String appPkg;
	private String title;
	private String appVersion;
	private String update;


	public CrawlingResultData(String appId, String appPkg, String title, String appVersion, String update) {
		super();
		this.appId = appId;
		this.appPkg = appPkg;
		this.title = title;
		this.appVersion = appVersion;
		this.update = update;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppPkg() {
		return appPkg;
	}

	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	
	@Override
	public String toString() {
		return "Data [appId=" + appId + ", title=" + title+ ", appVersion=" + appVersion + ", update=" + update + "]";
	}
}
