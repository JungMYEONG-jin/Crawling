package com.shinhan.market.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author parkyk
 * XML 파서 (XPath)
 * 샘플은 아래 main 쪽 주석 풀고 사용
 */
public class XMLParser {
	private XPath xpath = null;
	private Document document = null;

	public XMLParser() {
		xpath = XPathFactory.newInstance().newXPath();
	}
	
	public void init(String xml) throws ParserConfigurationException, SAXException, IOException{
		InputSource is = new InputSource(new StringReader(xml));
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = parser.parse(is);		
	}

	public String parse(String node) throws XPathExpressionException {
		return (String) xpath.evaluate(node, document, XPathConstants.STRING);
	}

	public Node parseNode(String node) throws XPathExpressionException {
		return (Node) xpath.evaluate(node, document, XPathConstants.NODE);
	}

	public NodeList parseNodeList(String node) throws XPathExpressionException {
		return (NodeList) xpath.evaluate(node, document, XPathConstants.NODESET);
	}
	
	public Document getDocument(){
		return document;
	}
	
	public XPath getXPath(){
		return xpath;
	}
	
//	public static void main(String[] args) {
//		
//		String xml = "<root>"
//					+ "<row>"
//					+ "<col1 id='c1'>揶쏉옙</col1>"
//					+ "<col2 id='c2' val='val2'>揶쏉옙</col2>"
//					+ "</row>"
//					+ "<row>"
//					+ "<col1 id='c3'>揶쏉옙</col1>"
//					+ "<col2 id='c4'>揶쏉옙</col2>"
//					+ "</row>"
//					+ "</root>";
//		
//		try {
//			XMLParser parser = new XMLParser(xml);
//			NodeList cols = parser.parseNodeList("//row/col1");
//			for (int idx = 0; idx < cols.getLength(); idx++) {
//				System.out.println(cols.item(idx).getTextContent());
//			}
//			// 揶쏉옙 揶쏉옙 占쏙옙�빊�뮆�젾占쏙옙			
//			// id 揶쏉옙c2 占쏙옙Node占쏙옙val attribute 揶쏉옙揶쏉옙議뉛옙�끆由�
//			Node col2 = parser.parseNode("//*[@id='c2']");
//			System.out.println(col2.getAttributes().getNamedItem("val").getTextContent());
//			// val2 �빊�뮆�젾
//
//			System.out.println(parser.parse("//*[@id='c3']"));
//			
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//		} catch (XPathExpressionException e) {
//		}
//	}

}
