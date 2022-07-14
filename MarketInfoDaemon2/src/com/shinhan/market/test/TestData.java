package com.shinhan.market.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.shinhan.market.daemon.dao.MarketInfo;
import com.shinhan.market.daemon.dto.SendInfo;
import com.shinhan.market.util.XMLParser;

public class TestData {

	public static String propertyXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
			+ "<items>"
			+"<item>"
			+"<result>OK</result> "
			+"<dateList>"
			+"<dateInfo>"
			+"<endTime>185000</endTime>"
			+"</dateInfo>"
			+"<dateInfo>"
			+"<endTime>193000</endTime>"
			+"</dateInfo>"
			+"<dateInfo>"
			+"<endTime>070000</endTime>"
			+"</dateInfo>"
			+"</dateList>"
			+"</item>"
			+"</items>";
	

	public static void main(String[] args) {
//		try {
//			XMLParser parser = new XMLParser();
//			parser.init(propertyXML);
//			NodeList nodeList = parser.parseNodeList("//dateList/dateInfo/endTime");
//			for (int idx = 0; idx < nodeList.getLength(); idx++) {
//				String endTime = nodeList.item(idx).getTextContent();
//				System.out.println(endTime);
//			}			
//			
//		} catch (ParserConfigurationException e) {
//		} catch (SAXException e) {
//		} catch (IOException e) {
//		} catch (XPathExpressionException e) {
//		}
//
//		long regTime = 1510653785725L;
//		
//		Date date = new Date(regTime);
//		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
//		System.out.println("123 : " + sdf.format(date));
//		

//		Document doc = Jsoup.parse(propertyXML);
//		System.out.println(doc.select("result").text());
//		Elements node = doc.select("dateList");
//		
//		for(Element data : node){
//			System.out.println(data.getElementById("endTime").text());
//		}
//		System.out.println(PKG_AND.length);
//		System.out.println(APPID_AND.length);
//		
//		System.out.println(PKG_IOS.length);
//		System.out.println(APPID_IOS.length);
	}
	

	
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

	public LinkedList<SendInfo> getMarketSendInfoList(){
	
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
		String regDt = sdf.format(date);
		
		LinkedList<SendInfo> list = new LinkedList<SendInfo>();
		StringBuffer sb = new StringBuffer();
		
		int seq = 0;
		for (String pkg : PKG_AND) {
			sb.delete(0, sb.capacity());
			sb.append("https://play.google.com/store/apps/details?id=").append(pkg);
			SendInfo ds1 = new SendInfo("0",pkg, pkg, MarketInfo.OS_TYPE_AND, sb.toString(), "div[class=id-app-title]", "div[itemprop=softwareVersion]", "div[itemprop=datePublished]",regDt,regDt,"");	
//			ds1.setSeq(++seq);
			list.add(ds1);
		}
		
		for (String pkg : PKG_IOS) {
			sb.delete(0, sb.capacity());
			sb.append("https://itunes.apple.com/kr/app/id").append(pkg);
			SendInfo ds1 = new SendInfo("0",pkg, pkg, MarketInfo.OS_TYPE_IOS, sb.toString(), "h1[class=product-header__title]", "ul[class=version-history__items] li:first-child h4[class=version-history__item__version-number]", "ul[class=version-history__items] li:first-child time[class=version-history__item__release-date]",regDt,regDt, "");	
//			ds1.setSeq(++seq);
			list.add(ds1);
		}
		
		return list;
	}
	
	
	
}
