package com.shinhan.market.test;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shinhan.market.crawling.data.CrawlingResultData;

public class TestXMLData {
	public static void main(String[] args) {
	
		try {
			CrawlingResultData data = new CrawlingResultData("appid", "appPkg","title", "appVersion", "upte");
			CrawlingResultData data2 = new CrawlingResultData("2appid", "appPkg", "2title", "2appVersion", "2upte");
			HashMap<String, CrawlingResultData> ret = new HashMap<String, CrawlingResultData>();
			ret.put("appid", data);
			ret.put("appid2",data2);
			
			Document doc = createDocumentOutputXML(ret);				
			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer trfomer = tf.newTransformer();
				trfomer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				trfomer.setOutputProperty(OutputKeys.INDENT, "yes");

				DOMSource source = new DOMSource(doc);
				// Console출력용
				StreamResult res= new StreamResult(System.out);
				
				// FILE 배포
//				StreamResult res = new StreamResult(new FileOutputStream(new File("\\path")));
				trfomer.transform(source, res);
				
			} catch (TransformerConfigurationException e) {
			} catch (TransformerException e) {
			}
		} catch (ParserConfigurationException e) {
		}
		
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
}
/*<items>
<item>
 <public_app_list>
  <app_info>
   <app_id>sbank_android</app_id>
   <app_version>1.0.0</app_version>
   <update_date>20170913173022</update_date>
  </app_info>
  <app_info>
   <app_id>sbank_ios</app_id>
   <app_version>1.0.0</app_version>
   <update_date>20170913173022</update_date>
  </app_info>
  <app_info>
   <app_id>sbankmini_android</app_id>
   <app_version>1.0.0</app_version>
   <update_date>20170913173022</update_date>
  </app_info>
  <app_info>
   <app_id>sbankmini_ios</app_id>
   <app_version>1.0.0</app_version>
   <update_date>20170913173022</update_date>
  </app_info>
  .
  .
  .
  .
 </public_app_list>
</item>
</items>*/