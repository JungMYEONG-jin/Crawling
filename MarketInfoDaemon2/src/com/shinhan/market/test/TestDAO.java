package com.shinhan.market.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.shinhan.market.daemon.MarketDaemon;
import com.shinhan.market.daemon.dao.MarketInfo;
import com.shinhan.market.daemon.dao.MarketPropertyDao;
import com.shinhan.market.daemon.dto.SendInfo;
import com.shinhan.market.daemon.service.MarketService;
import com.shinhan.market.util.XMLParser;


public class TestDAO {
	

	public static String[] APPID_IOS = { 
		"smarttax_ios",
		"noface_ios",
		"poney_ios",
		"senior_ios",
		"missionplus_ios",
		"safe2chssf_ios",
		"ebond_ios",
		"mfolio_ios",
		"salimi_ios",
		"sbizbank_ios",
		"sbank_ios",
		"sbankmini_ios",
		"ssurtax_ios",
		"sregister_ios",
		"s_tongjang_ios",
		"sunnycalculator_ios",
		"sunnybank_ios",
		"sunnyalarm_ios",
		"foreignerbank_ios",
		"sunnyswatch_ios",
		"shinhangi_ios",
		"shinhansa_ios",
		"shinhancn_ios",
		"shinhankh_ios",
		"shinhanvn_ios",
		"shinhanid_ios",
		"shinhanca_ios",
		"shinhanjp_ios",
		"smailid_ios",
		"smailvn_ios",
		"smailcn_ios",
		"sunnyclub_ios"};
	
	public static String[] PKG_IOS = { 
			"1180862826", 
			"1194492265",
			"1201584268",
			"1174226278", 
			"486789090",
			"896037244", 
			"481951052", 
			"1169292742",
			"486872386", 
			"587766126", 
			"357484932", 
			"458061594", 
			"725400385",
			"1052014390", 
			"1039324990", 
			"1177867277", 
			"1006963773",
			"1163682534", 
			"1190468026", 
			"1062479593",
			"1131925580",
			"1093362779", 
			"1143462205", 
			"1071033100", 
			"1071033810",
			"1287409348", 
			"1093365921",
			"1312722934", 
			"1095954989",
			"1016762804", 
			"1249194034", 
			"1064933073" };
	
	public static String[] APPID_AND = {
			"smarttax_android",
			"noface_android",
			"poney_android",
			"senior_android",
			"missionplus_android",
			"smartotp_android",
			"safe2chssf_android",
			"ebond_android",
			"mfolio_android",
			"salimi_android",
			"sbizbank_android",
			"sbank_android",
			"sbankmini_android",
			"sbankminiplus_android",
			"ssurtax_android",
			"sregister_android",
			"s_tongjang_android",
			"sunnycalculator_android",
			"sunnymirror_android",
			"sunnybank_android",
			"sunnytime_android",
			"foreignerbank_android",
			"sunnyswatch_android",
			"shinhangi_android",
			"shinhancn_android",
			"shinhansa_android",
			"shinhankh_android",
			"shinhanvn_android",
			"shinhanid_android",
			"shinhanca_android",
			"shinhanjp_android",
			"smailid_android",
			"smailvn_android",
			"smailcn_android",
			"sunnyclub_android"};
	
	public static String[] PKG_AND = { 
			"com.shinhan.smarttaxpaper",
			"com.shinhan.noface", 
			"com.shinhan.poney",
			"com.shinhan.senior",
			"com.shinhan.missionbanking",
			"com.shinhan.smartotp",
			"com.safeon.approval",
			"com.ebond",
			"com.shinhan.smartfund",
			"com.shinhan.smartcaremgr",
			"com.shinhan.sbizbank",
			"com.shinhan.sbanking",
			"com.shinhan.sbankmini",
			"com.shinhan.sbankminiplus",
			"com.shinhan.trade.copper",
			"com.shinhan.register",
			"com.shinhan.mobilebankbook",
			"com.shinhan.sunnycalculator", 
			"com.shinhan.sunnymirror",
			"com.shinhan.speedup", 
			"com.shinhan.sunnyalarm",
			"com.shinhan.foreignerbank", 
			"com.shinhan.swatchbank",
			"com.shinhan.global.gi.bank",
			"com.shinhan.global.sa.bank",
			"com.shinhan.global.ca.bank",
			"com.shinhan.global.kh.bank",
			"com.shinhan.global.vn.bank", 
			"com.shinhan.global.id.bank",
			"com.shinhan.global.ca.bank",
			"com.shinhan.global.jp.bank",
			"com.shinhan.smailid", 
			"com.shinhan.smailvn",
			"com.shinhan.smailcn", 
			"com.shinhan.global.vn.sclub" };


	public static void main(String[] args) 
	{
		ApplicationContext context = new FileSystemXmlApplicationContext(new String[] { "classpath:common-context.xml"});
		MarketService dbm = (MarketService) context.getBean("DBManager");
		

//		System.out.println("Send insert Start");
//		try {
//			SendInfo sendInfo = new SendInfo();
//			sendInfo.setAppId("com.shinhan.sbanking");
//			sendInfo.setSendStatus(SendInfo.SEND_RESULT_OK);
//			sendInfo.setErrorMsg(" ");
//			sendInfo.setReqUserId("12041041");
//			dbm.insertSendInfo(sendInfo);
////			List<SendInfo> list = dbm.getSendInfoList();
////			for(SendInfo info : list){
////				System.out.println(info.toString());				
////			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//		System.out.println("Send insert END");
//		
//		
//		System.out.println("Send LIST Start");
//		try {
//			List<SendInfo> list = dbm.getSendInfoList();
//			for(SendInfo info : list){
//				System.out.println(info.toString());				
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//		System.out.println("Send LIST END");
//		
//		
//		dbm.insertPeriodMarketSendInfo();
		
		System.out.println("Send insert Start");
		int idx = 0;
		for(String pkg : PKG_AND){
			String appId = APPID_AND[idx];
			dbm.testInsertMarketData(appId, pkg);			
			idx++;
		}

		idx = 0;
		System.out.println("Send insert IOS Start");
		for(String pkg : PKG_IOS){
			String appId = APPID_IOS[idx];
			dbm.testInsertMarketDataIOS(appId, pkg);			
			idx++;
		}
		
		
//		System.out.println("1111111");
//		try {
//			MarketPropertyDao propertyDAO = dbm.getPropertyInfo();
//			System.out.println(propertyDAO.toString());
//			
//			String xml = propertyDAO.getPropertyData();
//			
//			try {
//				XMLParser parser = new XMLParser();
//				parser.init(xml);
//				NodeList nodeList = parser.parseNodeList("//setting_time_list/time_info/checkTime");
//				for (int idx = 0; idx < nodeList.getLength(); idx++) {
//					String endTime = nodeList.item(idx).getTextContent();
//					System.out.println(endTime);
//				}			
//				
//			} catch (ParserConfigurationException e) {
//			} catch (SAXException e) {
//			} catch (IOException e) {
//			} catch (XPathExpressionException e) {
//			}	
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println("2222222");
//		
//		String certificatePath = "ShinhanGori_Production.p12";
//        String certificatePassword = "tlsgks01";
//
//        String deviceToken = "d4c80e25e17cc6eda71b5ab472b332aa63bdf102899cb79a718dcaca9d9d7696";
//        List<String> tokens = new ArrayList<String>();
//        tokens.add(deviceToken);
//        
//        // true : 단일 발송, false : 다중 발송
//        boolean sendCount = true; 
//        
//         try{
//             
//             PushNotificationPayload payLoad = new PushNotificationPayload();
//             payLoad.addAlert("message");
//             payLoad.addBadge(1);
//             payLoad.addSound("default");
//             
//             
//             PushNotificationManager pushManager = new PushNotificationManager();
//             
//             pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
//             
//             List<PushedNotification> notifications = new ArrayList<PushedNotification>();
//             
//             if (sendCount){
//                  
//                Device device = new BasicDevice();
//                device.setToken(tokens.get(0));
//                PushedNotification notification = pushManager.sendNotification(device, payLoad);
//                notifications.add(notification);
//                
//             }else{
//                  
//                 List<Device> device = new ArrayList<Device>();
//                 for (String token : tokens){
//                     device.add(new BasicDevice(token));
//                 }
//                 notifications = pushManager.sendNotifications(payLoad, device);
//                  
//             }
//              
//              List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
//              List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
//              int failed = failedNotifications.size();
//              int successful = successfulNotifications.size();
//              System.out.println("fiaild = "+ failed );
//              System.out.println("successful = "+ successful);
//
//             
//         }catch(KeystoreException ke){
//             System.out.println("KeystoreException" + ke.getMessage());
//         }catch(CommunicationException ce){
//             System.out.println("Communication" + ce.getMessage());
//         }catch (Exception e) {
//             e.printStackTrace();
//         }
		
	}
	
}
