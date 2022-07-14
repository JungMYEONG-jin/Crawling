package com.shinhan.market.daemon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessControlException;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.shinhan.market.daemon.dao.MarketInfo;
import com.shinhan.market.daemon.service.MarketService;
import com.shinhan.market.property.MarketProperty;

public class DaemonStarter {

	public static void main(String[] args) throws IOException {
		
		File file = new File(".");
		InetAddress inet = InetAddress.getLocalHost();
		
		String hostName = inet.getHostName();
		System.out.println("file.getCanonicalPath():" + file.getCanonicalPath());
		System.out.println("inet.getHostAddress():" + inet.getHostAddress());
		System.out.println("inet.getHostName():" + hostName);

		String classPath = null;
		
		if ("etcwb1d".equals(hostName)) {
			classPath = "classpath:common-context.xml";
		} else if ("smtwb1p".equals(hostName)) {
			classPath = "classpath:common-context-real-1p.xml";
		}else if ("smtwb2p".equals(hostName)){
			classPath = "classpath:common-context-real-2p.xml";
		}else {
			System.out.println("Invalid host name");
			System.exit(-1);
		}
		
		System.out.println("classPath ==> "+ classPath);
		ApplicationContext context = new FileSystemXmlApplicationContext(new String[] { classPath});
		MarketService dbService = (MarketService) context.getBean("DBManager");
		MarketDaemon daemon = (MarketDaemon) context.getBean("daemon");
		daemon.setMarketDBService(dbService);
		
		Thread daemonThread = new Thread(daemon);
		daemonThread.start();
	}
}
