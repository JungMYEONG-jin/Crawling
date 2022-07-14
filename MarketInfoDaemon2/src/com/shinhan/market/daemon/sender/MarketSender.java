package com.shinhan.market.daemon.sender;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shinhan.market.crawling.Crawling;
import com.shinhan.market.crawling.data.CrawlingResultData;
import com.shinhan.market.daemon.dto.SendInfo;
import com.shinhan.market.daemon.service.MarketService;
import com.shinhan.market.errorcode.ErrorCode;
import com.shinhan.market.exception.CrawlingException;
import com.shinhan.market.exception.CreateFileException;
import com.shinhan.market.exception.GetSendInfoListException;
import com.shinhan.market.exception.SendInfoListException;
import com.shinhan.market.property.MarketProperty;

/**
 * @author parkyk
 * FILE UPDATE LIMIT MIN동안 데이터 추가가 없었다면 파일로 쓴다.
 */
public class MarketSender extends Thread {

	public Logger m_log = Logger.getLogger(getClass());
	
	private MarketProperty propertyMarket = null;
	private MarketService serviceMarket = null;
	
	private Crawling crawling = null;

	private long regTime = MarketProperty.INIT_VALUE;
	
	private int m_nMaxArraySendSeqCnt = 500;

	private HashMap<String, CrawlingResultData> mapResCrawling = new HashMap<String, CrawlingResultData>();

	private int mCheckCnt;

	public MarketSender() {
		super();
		initialize();		
	}
	
	public MarketSender(MarketService dbManager, MarketProperty marketProperty) {
		super();
		
		propertyMarket = marketProperty;
		serviceMarket = dbManager;
		
		if(propertyMarket == null){
			throw new NullPointerException("Market Property의 값이 Null입니다. MarketProperty를 확인햊 주세요.");
		}

		if(serviceMarket == null){
			throw new NullPointerException("MarketService가 Null입니다. Market Service를 확인해주세요.");
		}
		
		initialize();		
	}
	
	private void initialize() {
		m_log.info("PushDBSender Thread initialize OK..");
	}
	
	public void run() {
		try {
			while (true) {

				if(mCheckCnt > 60){
					mCheckCnt = 0;
					m_log.info("================ SEND_DAEMON ================");
				
				}

				try {
					List<SendInfo> sendInfoList = serviceMarket.getSendInfoList();
					processSendInfoList(sendInfoList);
					isCheckCreateFile();
					
				} catch (GetSendInfoListException e){
					m_log.info("GetSendInfoListException : " + e.getMessage());
				} catch (SendInfoListException e) {
					m_log.info("SendInfoListException : " + e.getMessage());
				} catch (CreateFileException e) {
					m_log.info("CreateFileException : " + e.getMessage());
				} catch (Exception e) {
					m_log.info("Send Message Exception : "+ e.getMessage());
				}
				
				try {
					processSleep();
				} catch (InterruptedException e) {
					ErrorCode.LogError(getClass(), "B1003", e);
				}
				
				mCheckCnt++;
			}
			
		} catch(Exception ex) {
			ErrorCode.LogError(getClass(), "B1000", ex);
			serviceMarket.exceptionDBSaveAndAdminappPushSend(ex);			
		} finally {
			if(mapResCrawling != null && mapResCrawling.isEmpty() == false){
				createFile(mapResCrawling);
			}
		}
	}
	
	private void isCheckCreateFile() throws CreateFileException {
		try {
			// FILE UPDATE LIMIT MIN동안 데이터 add가 없었다면 파일로 쓴다.
			if (regTime != MarketProperty.INIT_VALUE) {
				long diffTIme = ((System.currentTimeMillis() - regTime));

				m_log.info("DiffTime : " + diffTIme + " limitTime : "
						+ MarketProperty.FILE_UPDATE_LIMIT_SEC);

				if (diffTIme > MarketProperty.FILE_UPDATE_LIMIT_SEC) {

					createFile(mapResCrawling);

					// reset
					regTime = MarketProperty.INIT_VALUE;

					if (mapResCrawling != null) {
						mapResCrawling.clear();
					} else {
						mapResCrawling = new HashMap<String, CrawlingResultData>();
					}
				}
			}
		} catch (Exception e) {
			throw new CreateFileException("XML결과 파일 생성중 에러가 발생하엿습니다.", e);
		}
	}

	public void processSendInfoList(List<SendInfo> sendInfoList) throws SendInfoListException {
		
		try {
			synchronized (sendInfoList) {
				if (sendInfoList.isEmpty() == false) {
					Crawling crawling = getCrawling();
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddHHmmSS");
					String startTime = sdf.format(date);

					m_log.info("ProcessSendInfo Start Time : " + startTime);

					int nArraySendSeqCnt = 0;
					String arraySendSeq = "";

					m_log.info("Crawling Start");
					for (SendInfo sendInfo : sendInfoList) {

						try {
							if (sendInfo != null) {

								// set 등록시간
								regTime = System.currentTimeMillis();

								CrawlingResultData ret = crawling.crawling(sendInfo);
								if (ret != null) {
									m_log.info("Crawling 성공 : "
											+ ret.toString());

									mapResCrawling.put(ret.getAppId(), ret);

									// Max Seqence 체크

									// TODO parkyk
									if (nArraySendSeqCnt == 0) {
										arraySendSeq = arraySendSeq
												+ sendInfo.getSeq();
									} else {
										arraySendSeq = arraySendSeq + ","
												+ sendInfo.getSeq();
									}

									++nArraySendSeqCnt;

									if (nArraySendSeqCnt >= m_nMaxArraySendSeqCnt) {

										updateSendInfoArray(arraySendSeq);
										nArraySendSeqCnt = 0;
										arraySendSeq = "";
									}

								} else {
									m_log.info("Crawling 실패");
									sendInfo.setSendStatus(SendInfo.SEND_RESULT_CRAWLING_FAIL);
									updateSendInfoError(sendInfo,
											sendInfo.getSeq());
								}

							} else {
								m_log.info("Market데이터가 없습니다. (object is null)");
							}

						} catch (CrawlingException e) {
							ErrorCode.LogError(getClass(), "B1002", e);
							sendInfo.setErrorMsg(e.getMessage());
							sendInfo.setSendStatus(SendInfo.SEND_RESULT_CRAWLING_FAIL);
							updateSendInfoError(sendInfo, sendInfo.getSeq());

						} catch (Exception e) {
							ErrorCode.LogError(getClass(), "B1001", e);
							sendInfo.setErrorMsg(e.getMessage());
							sendInfo.setSendStatus(SendInfo.SEND_RESULT_ERROR);
							updateSendInfoError(sendInfo, sendInfo.getSeq());
						}

						try {
							Thread.sleep(MarketProperty.SEND_DAEMON_MARKET_SEND_DELAY_SEC);
						} catch (InterruptedException e) {
							try {
								Thread.sleep(MarketProperty.DEFAULT_SEND_DAEMON_MARKET_SEND_DELAY_SEC);
							} catch (InterruptedException e1) {
							}
						}
					}

					m_log.info("Crawling End");

					/**
					 * HIST 테이블로 옮기고 삭제 하기
					 */
					if (nArraySendSeqCnt > 0 && !arraySendSeq.equals("")) {
						// 성공!
						updateSendInfoArray(arraySendSeq);
						sendInfoList.clear();
					}
				}
			}
		} catch (Exception e) {
			
			throw new SendInfoListException("크롤링 발송 중 에러가 발생 하엿습니다.", e);
		}
	}	
	
	private void updateSendInfoError(SendInfo sendInfo, String arraySendSeq) {
		// TODO 
		if (sendInfo != null) {
			serviceMarket.updateSendInfo(sendInfo);
		}			
		
		// TODO 
		serviceMarket.insertSendHistArray(sendInfo, arraySendSeq);
		serviceMarket.deleteSendInfoArray(arraySendSeq);
	}

	private boolean createFile(HashMap<String, CrawlingResultData> mapResCrawling) {
		
		m_log.info("Create File Start");
		
		Document doc = null;
		try {
			doc = createDocumentOutputXML(mapResCrawling);
		} catch (ParserConfigurationException e1) {
			ErrorCode.LogError(getClass(), "D1000");
		}
		
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trfomer = tf.newTransformer();
			trfomer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			trfomer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			// Console출력용
//			StreamResult res= new StreamResult(System.out);
			
			// FILE 배포
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = sdf.format(date);
			String path = propertyMarket.getOutput_xml_path();
			String fileName = String.format(propertyMarket.getOutput_xml_file_name(), dateString);
			m_log.info("XML Output File Path : " + path + fileName);
			StreamResult res = new StreamResult(new FileOutputStream(new File(path, fileName)));
			trfomer.transform(source, res);
			
		} catch (Exception e) {
			ErrorCode.LogError(getClass(), "D1001", e);
		}
		
		m_log.info("Create File End");
		
		return false;
	}

	public void updateSendInfoArray(String arraySendSeq) {
		serviceMarket.insertSendHistArray(arraySendSeq);
		serviceMarket.deleteSendInfoArray(arraySendSeq);			
	}
	
	public void deleteSendInfoArray(String arraySendSeq) {
		m_log.debug("arraySendSeq ==> "+arraySendSeq);
		serviceMarket.deleteSendInfoArray(arraySendSeq);		
	}
	
	private Crawling getCrawling() {
		if(crawling == null){
			crawling = new Crawling();
		}
		return crawling;
	}
	
	private static Document createDocumentOutputXML(HashMap<String, CrawlingResultData> mapResult) throws ParserConfigurationException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc =docBuilder.newDocument();
		
		Element rootElement = doc.createElement("items");
		Element item = doc.createElement("item");
		Element public_app_list = doc.createElement("public_app_list");

		if(mapResult != null){
			for(String key : mapResult.keySet()){
				if(key == null || key.isEmpty()){
					continue;
				}
				
				CrawlingResultData data = mapResult.get(key);
				
				if(data == null){
					continue;
				}
				
				Element app_info = doc.createElement("app_info");
				
				addChild(doc, app_info, "app_id", data.getAppId());
				addChild(doc, app_info, "app_version", data.getAppVersion());
				addChild(doc, app_info, "update_date", data.getUpdate());
				
				public_app_list.appendChild(app_info);				
			}
		}
		
		item.appendChild(public_app_list);
		doc.appendChild(rootElement);
		rootElement.appendChild(item);
		return doc;
	}

	private static void addChild(Document doc, Element app_info, String key, String value) {
		if(value != null && value.isEmpty() == false){
			Element elUptDate = doc.createElement(key);
			elUptDate.appendChild(doc.createTextNode(value));
			
			app_info.appendChild(elUptDate);
		}
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
		Thread.sleep(MarketProperty.SEND_DAEMON_SLEEP_TIME);
	}

}
