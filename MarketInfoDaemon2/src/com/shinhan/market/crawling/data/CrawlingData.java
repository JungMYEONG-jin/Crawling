package com.shinhan.market.crawling.data;


public interface CrawlingData {
	String getAppId();
	String getAppPkg();
	String getOsType();
	String getStoreUrl();
	String getTitleNode();
	String getVersionNode(); 
	String getUpdateNode();
	String getMethodType();
}
