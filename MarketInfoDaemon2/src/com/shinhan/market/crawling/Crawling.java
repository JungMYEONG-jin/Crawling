package com.shinhan.market.crawling;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.FileCopyUtils;

import com.shinhan.market.crawling.data.CrawlingData;
import com.shinhan.market.crawling.data.CrawlingResultData;
import com.shinhan.market.errorcode.ErrorCode;
import com.shinhan.market.exception.CrawlingException;

public class Crawling {

	private int CONN_TIME_OUT = 1000 * 30;
	
	private StringBuffer stringBuffer = null;

	private StringBuffer getStrBuf(){
		if(stringBuffer == null){
			stringBuffer = new StringBuffer();
		} else {
			stringBuffer.delete(0, stringBuffer.capacity());
		}
			
		return stringBuffer;
	}
	
	public CrawlingResultData crawling(CrawlingData crawlingData) throws CrawlingException, Exception {
		try {		
			if(crawlingData == null){
				ErrorCode.LogInfo(getClass(), "C1000");
				return null;
			}
		
			String storeUrl = crawlingData.getStoreUrl();
			String appPkg   = crawlingData.getAppPkg();
			
			String retHtmlString = null;
			
			if(storeUrl != null && appPkg != null){
				StringBuffer sb = getStrBuf();
				sb.append(storeUrl).append(appPkg);
				retHtmlString = getStringDataPostSSL(sb.toString(), crawlingData.getMethodType());				
			} else {
				ErrorCode.LogInfo(getClass(), "C1001");
				return null;
			}
			
			// 현재 jsoup 안됨.
//			Document doc2 = Jsoup.connect("https://play.google.com/store/apps/details?id=com.shinhan.sbanking").post();
			Document doc = Jsoup.parse(retHtmlString);
		
			if(doc == null){
				ErrorCode.LogInfo(getClass(), "C1002");
				return null;
			}
			
			String titleNode = crawlingData.getTitleNode();
			
			String title = selectNode(doc, titleNode); 
			
			
			String dateNode = crawlingData.getUpdateNode();
			
			String date = selectNode(doc, dateNode); 

			// appVersion 예외처리
			String appVer = parseAppVersion(doc, crawlingData.getVersionNode());
			
			if(appVer == null || appVer.isEmpty()){
				return null;
			}
			
			return new CrawlingResultData(crawlingData.getAppId(), crawlingData.getAppPkg(), title, appVer, date);
		
		}
		
		catch (IOException e) {
			ErrorCode.LogError(getClass(), "C1003");
		}
		
		return null;
	}

	private String selectNode(Document doc, String node) {
		
		try {
			String ret = null;
			
			if (node != null) {
				//[first://]가 node안에 있으면 가장 첫번째 노드로 검색한다.
				if (node.contains("[first://]")) {
					node = node.replace("[first://]", "");
					ret = doc.select(node).first().text();
				} else {
					ret = doc.select(node).text();
				}
			}
			
			if(ret == null){
				ret = "";
			}
			return ret;
		} catch (Exception e) {
			return "";
		}
	}
	
	public String parseAppVersion(Document doc, String appVersion) {
		try {
			if(appVersion.isEmpty() == false) {
				Elements elAppVer = doc.select(appVersion);	
				appVersion = elAppVer.text();
				appVersion = appVersion.replaceAll("[^0-9.]", "");
			}

			if(appVersion == null || appVersion.isEmpty() || appVersion.equals(".")) {
				Elements change = doc.select("div.recent-change");
				for(String str : change.eachText()) {
					if(str.toLowerCase().contains("ver")) {
						appVersion = str.replaceAll("[^0-9.]", "");
						break;
					}
				}						
			}
			
			if(appVersion == null || appVersion.isEmpty()) {
				try {
					if (appVersion != null) {
						//[first://]가 node안에 있으면 가장 첫번째 노드로 검색한다.
						if (appVersion.contains("[first://]")) {
							appVersion = appVersion.replace("[first://]", "");
							appVersion = doc.select(appVersion).first().text();
							appVersion = appVersion.replaceAll("[^0-9.]", "");
						} else {
							appVersion = doc.select(appVersion).text();
							appVersion = appVersion.replaceAll("[^0-9.]", "");
						}
					}
				} catch (Exception e) {
					return "";
				}
			}
			
		} catch (Exception e) {
			return "";
		}
		return appVersion;
		
	}
	
	
	public String getStringDataPostSSL(String targetUrl, String methodType) throws Exception {

		String result = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		SSLContext sslContext = SSLContext.getInstance("SSL");

		try {
			X509TrustManager trustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// LOGGER.debug("checkClientTrusted");
					
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// LOGGER.debug("checkServerTrusted");
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					// LOGGER.debug("getAcceptedIssuers");
					
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { trustManager },
					new SecureRandom());
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", 443, socketFactory);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			
			HttpParams httpParam = httpClient.getParams();		
			org.apache.http.params.HttpConnectionParams.setConnectionTimeout(httpParam, CONN_TIME_OUT);
			org.apache.http.params.HttpConnectionParams.setSoTimeout(httpParam, CONN_TIME_OUT);
			
			HttpRequestBase http = null;
			try {
				if (methodType.equals("1")) {
					http = new HttpGet(targetUrl);
				} else if(methodType.equals("3")) {
					http = new HttpDelete(targetUrl);
				} else if(methodType.equals("4")) {
					http = new HttpPut(targetUrl);
				} else {
					http = new HttpPost(targetUrl);
				}
			} catch (Exception e) {
				http = new HttpPost(targetUrl);
			}
			HttpResponse response = null;
			HttpEntity entity = null;
			HttpRequest request = null;
			String responseBody = null;
			/**
			 * 호출 후 OUTPUT
			 */
			// Time Out
			response = httpClient.execute(http);
			entity = response.getEntity();
			responseBody = EntityUtils.toString(entity, "UTF-8");
			
			result = responseBody;
		} catch (Exception e) {
			throw new CrawlingException(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}
}
