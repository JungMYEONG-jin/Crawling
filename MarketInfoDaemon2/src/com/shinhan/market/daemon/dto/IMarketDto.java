package com.shinhan.market.daemon.dto;

public interface IMarketDto {
	public int getSeq();
	public void setSendStatus(String sendStatus);
	public String getSendStatus();
	public String getErrorMsg();
	
}
