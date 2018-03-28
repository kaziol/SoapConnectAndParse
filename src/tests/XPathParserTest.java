package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.xpath.XPath;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import pckg.XPathParser;

class XPathParserTest {
	List<String> expectedResultTd = Arrays.asList("Apple","Banana","Potato","Orange");

	
	final String inputXml = "<h:table xmlns:h=\"http://www.w3.org/TR/html4/\">" + 
			"  <h:tr>\r\n" + 
			"    <h:td>Apple</h:td>\r\n" + 
			"    <h:td>Banana</h:td>\r\n" + 
			"    <h:tr>Kiwi</h:tr>\r\n" + 
			"    <h:td>Potato</h:td>\r\n" + 
			"    <h:td>Potato</h:td>\r\n" + 
			"  </h:tr>\r\n" + 
			"  <h:tr>\r\n" + 
			"    <h:td>Orange</h:td>\r\n" + 
			"  </h:tr>\r\n" + 
			"</h:table>";
	
	final String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
			"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" + 
			"  <soap12:Body>\r\n" + 
			"    <ws:GetWeatherResponse xmlns:ws=\"http://www.webserviceX.NET\">\r\n" + 
			"      <ws:GetWeatherResult>ExpectedResult</ws:GetWeatherResult>\r\n" + 
			"    </ws:GetWeatherResponse>\r\n" + 
			"  </soap12:Body>\r\n" + 
			"</soap12:Envelope>\r\n";
	
	@Test
	void testXPathParser() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		XPathParser xpp = new XPathParser(inputXml);
		Field docF = XPathParser.class.getDeclaredField("doc");
		docF.setAccessible(true);
		Field xpathF = XPathParser.class.getDeclaredField("xpath");
		xpathF.setAccessible(true);
		
		Document doc = (Document) docF.get(xpp);
		XPath xpath = (XPath) xpathF.get(xpp);

		
		assertNotNull(doc);
		assertNotNull(xpath);
		NodeList nl=doc.getElementsByTagNameNS("*", "td");
		
		Set<String> hs = new HashSet<>();
		
		for(int i=0;i<nl.getLength();i++) {
			hs.add(nl.item(i).getTextContent());
		}
		assertTrue(hs.containsAll(expectedResultTd));
	}

	@Test
	void testGetXpathValues() {
		XPathParser xpp = new XPathParser(inputXml);
		List<String> result = xpp.getXpathValues("/h:table/h:tr/h:td");
		
		assertTrue(result.containsAll(expectedResultTd));
		assertFalse(result.contains("Kiwi"));
	}
	
	@Test
	void testGetXpathValuesSOAP() {
		XPathParser xpp = new XPathParser(soapXML);
		List<String> result = xpp.getXpathValues("/soap12:Envelope/soap12:Body/ws:GetWeatherResponse/ws:GetWeatherResult");		
		assertEquals("ExpectedResult", result.get(0));
		
		result = xpp.getXpathValues("//ws:GetWeatherResult");
		assertEquals("ExpectedResult", result.get(0));


	}
	
	
	
	

}
