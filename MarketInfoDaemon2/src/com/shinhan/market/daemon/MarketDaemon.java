package com.shinhan.market.daemon;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.NodeList;

import com.shinhan.market.daemon.dao.MarketPropertyDao;
import com.shinhan.market.daemon.sender.MarketSender;
import com.shinhan.market.daemon.service.MarketService;
import com.shinhan.market.errorcode.ErrorCode;
import com.shinhan.market.property.MarketProperty;
import com.shinhan.market.util.TimeCheker;
import com.shinhan.market.util.XMLParser;

public class MarketDaemon implements Runnable {

	public static final boolean DEV = true;
	
	public Logger m_log = Logger.getLogger(getClass());
	
	private MarketProperty propertyMarket = null;
	private MarketService serviceMarket = null;
	
	private MarketSender senderThread = null;

	private LinkedList<TimeCheker> listDateTime = new LinkedList<TimeCheker>();
	private XMLParser xmlParser = new XMLParser();

	private String xmlSettingData;

	public void setMarketProperty(MarketProperty marketProperty) {
		this.propertyMarket = marketProperty;
	}
	
	public void setMarketDBService(MarketService dbService){
		this.serviceMarket = dbService;
	}
	
	public void initialize() {
		m_log.info("Thread initialize OK..");

		// Sender Daemon 초기화
		if(senderThread != null){
			senderThread.interrupt();
			senderThread = null;
		}

		senderThread = new MarketSender(serviceMarket, propertyMarket);		
		senderThread.start();
	}

	@Override
	public void run() {
		initialize();
		
		while (true) {
			
			try {
				
				// 세팅 정보 변경 여부 확인
				if(isChangeSettingInfo()){
					// 변경 데이터가 있다면 설정정보 변경 
					m_log.info("init Setting Start");						
					
					initSetting();
					
					m_log.info("init Setting End");						
					
				}

				if(listDateTime.isEmpty()){
					m_log.info("Reset time start");						

					resetDateTime();				
					
					m_log.info("Reset time End");						
					
					if(DEV){
						for(TimeCheker time : listDateTime){
							Calendar cal = time.getDateTime();
							
							m_log.info("reset time : " + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + String.format("%02d", cal.get(Calendar.MINUTE)) + String.format("%02d",cal.get(Calendar.SECOND)));								

						}
					}
				}
				
				// 패킷을 보내야 하는지 확인한다.
				boolean isSendPacket = checkSendPacket();
				
				if(isSendPacket){
					m_log.info("Market Daemon Send Packet Start");						
					
					// 보낼 패킷데이터가 있다. 
					insertMarketSendInfo();
					
					m_log.info("Market Daemon Send Packet End");						

				} 
			} catch (Exception e){
				ErrorCode.LogError(getClass(), "A1006",e);
			}
				
			try {
				processSleep();
			} catch (InterruptedException e) {
				ErrorCode.LogError(getClass(), "A1004",e);
			}
		}
	}

	private void insertMarketSendInfo() {
		try {
			serviceMarket.insertPeriodMarketSendInfo();
		} catch (Exception e) {
			ErrorCode.LogError(getClass(), "A1003",e);
		}			
	}

	private void resetDateTime()  {

		if(listDateTime != null){
			listDateTime.clear();				
		} else {
			listDateTime = new LinkedList<TimeCheker>();
		}
		
		try {
			NodeList nodeList = xmlParser.parseNodeList("//setting_time_list/time_info/checktime");
			for (int idx = 0; idx < nodeList.getLength(); idx++) {
				String endTime = nodeList.item(idx).getTextContent();
				TimeCheker timeChecker = new TimeCheker(endTime);
				if (timeChecker.isVaildate()) {
					listDateTime.add(timeChecker);
				}
			}
			
		} catch (XPathExpressionException e) {
			ErrorCode.LogError(getClass(), "A1002", e);
		}
		
		// time 정렬
		Collections.sort(listDateTime, new CompareDateTime());
		
		
		m_log.info("\n\n\n");
		for(TimeCheker time : listDateTime){
			m_log.info(time.toString());
		}
		m_log.info("\n\n\n");
	}
	
	public class CompareDateTime implements Comparator<TimeCheker>{

		@Override
		public int compare(TimeCheker arg0, TimeCheker arg1) {
			return arg0.getDateTime().compareTo(arg1.getDateTime());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkSendPacket() {

		if(listDateTime != null && listDateTime.isEmpty() == false){
			
			TimeCheker dtchk = listDateTime.getFirst();
			if(dtchk != null){
				try {
					if(dtchk.checkElapseTime()){
						if(listDateTime != null && listDateTime.isEmpty() == false){
							listDateTime.removeFirst();						
						}
						
						return true;
					} else {
					}
				} catch (Exception e) {
					// 해당 리스트 삭제. 
					ErrorCode.LogError(getClass(), "A1005", e);
					
					if(listDateTime != null && listDateTime.isEmpty() == false){
						listDateTime.removeFirst();						
					}
				}
			}
		}
		
		return false;
	}


	/**
	 * @author parkyk
	 * @param delay_time
	 * 
	 * 슬립 체크 
	 * @throws InterruptedException 
	 * 
	 */
	private void processSleep() throws InterruptedException {
		// Sleep 
		Thread.sleep(MarketProperty.MARKET_DAEMON_SLEEP_TIME);
	}

	private void initSetting() {
		// 세틸 정보 변경
		MarketPropertyDao propertyInfo = getPropertyInfo();
		xmlSettingData = propertyInfo.getPropertyData();
		
		if(xmlSettingData == null || xmlSettingData.isEmpty()){
			ErrorCode.LogError(getClass(), "A1000");
		}
		
		try {
			xmlParser.init(xmlSettingData);
		} catch (Exception e) {
			ErrorCode.LogError(getClass(), "A1001", e);
		}
		
		// 타임설정 클리러
		if(listDateTime != null){
			listDateTime.clear();			
		} else {
			listDateTime = new LinkedList<TimeCheker>();
		}

		
	}

	MarketPropertyDao propertyDAO = null;
	
	public MarketPropertyDao getPropertyInfo() {
		return propertyDAO;
	}

	private boolean isChangeSettingInfo() {
		try {
			MarketPropertyDao newPropertyDAO = serviceMarket.getPropertyInfo();
			if(propertyDAO == null){
				// 기존에 등록되어있는 property 정보가 없다. 설정하자.
				setPropertyInfo(newPropertyDAO);
				return true;
				
			} else {
				
				// 등록되어있는 property정도가 있다. 비교하자.
				if(propertyDAO.isChangeValue(newPropertyDAO)){
					System.out.println("444 -1 ");
					setPropertyInfo(newPropertyDAO);
					System.out.println("555 - 1");
					return true;
				}
				System.out.println("77777777777777777777");
			}	
			System.out.println("88888888888888888888");
		} catch (Exception e) {
			System.out.println("000");
			ErrorCode.LogError(getClass(), "A1007", e);
		}
		System.out.println("99999999999999999999999999");
		return false;
	}

	private void setPropertyInfo(MarketPropertyDao newPropertyDAO) {
		propertyDAO = newPropertyDAO;		
		
		String xmlSettingData = newPropertyDAO.getPropertyData();
		if(xmlSettingData != null && xmlSettingData.isEmpty() == false){
			Document doc = Jsoup.parse(xmlSettingData);		
			
			// marketDaemonSleepTime Set
			int retTime = setSleepTime(doc,"market_daemon_sleep_sec");
			
			if(retTime == MarketProperty.INIT_VALUE){
				MarketProperty.MARKET_DAEMON_SLEEP_TIME = MarketProperty.DEFAULT_MARKET_DAEMON_SLEEP_TIME;
			} else {
				MarketProperty.MARKET_DAEMON_SLEEP_TIME = retTime;
			}
			
			retTime = setSleepTime(doc,"send_daemon_sleep_sec");
			
			if(retTime == MarketProperty.INIT_VALUE){
				MarketProperty.SEND_DAEMON_SLEEP_TIME = MarketProperty.DEFAULT_SEND_DAEMON_SLEEP_TIME;
			} else {
				MarketProperty.SEND_DAEMON_SLEEP_TIME = retTime;
			}
			
			retTime = setSleepTime(doc,"send_daemon_market_send_delay_sec");
			
			if(retTime == MarketProperty.INIT_VALUE){
				MarketProperty.SEND_DAEMON_MARKET_SEND_DELAY_SEC = MarketProperty.DEFAULT_SEND_DAEMON_MARKET_SEND_DELAY_SEC;
			} else {
				MarketProperty.SEND_DAEMON_MARKET_SEND_DELAY_SEC = retTime;
			}
			
			retTime = setSleepTime(doc,"file_update_limit_sec");
			
			if(retTime == MarketProperty.INIT_VALUE){
				MarketProperty.FILE_UPDATE_LIMIT_SEC = MarketProperty.DEFAULT_FILE_UPDATE_LIMIT_SEC;
			} else {
				MarketProperty.FILE_UPDATE_LIMIT_SEC = retTime;
			}
			
		} else {
			ErrorCode.LogError(getClass(), "A1008");
		}
	}

	private int setSleepTime(Document doc, String docNode) {
		int marketDaemonSleepSec = MarketProperty.INIT_VALUE;
		try {
			String sec = doc.select(docNode).text();
			marketDaemonSleepSec = Integer.parseInt(sec);
		} catch (Exception e) {
			marketDaemonSleepSec = MarketProperty.INIT_VALUE;
		}

		return marketDaemonSleepSec;
	}
}
