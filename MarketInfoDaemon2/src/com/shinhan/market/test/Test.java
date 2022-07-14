package com.shinhan.market.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.shinhan.market.daemon.MarketDaemon;
import com.shinhan.market.daemon.service.MarketService;


public class Test {

	public static void main(String[] args) 
	{
		ApplicationContext context = new FileSystemXmlApplicationContext(new String[] { "classpath:common-context.xml"});

		MarketService dbService = (MarketService) context.getBean("DBManager");
		MarketDaemon daemon = (MarketDaemon) context.getBean("daemon");
		daemon.setMarketDBService(dbService);
		
		Thread daemonThread = new Thread(daemon);
		daemonThread.start();

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
