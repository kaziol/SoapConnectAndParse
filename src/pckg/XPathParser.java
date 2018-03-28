package pckg;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathParser {
	private Document doc;
	private XPath xpath;
	public XPathParser(String input) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc=builder.parse(new InputSource(new ByteArrayInputStream(input.getBytes("utf-8"))));
			
			// Create XPathFactory object
			XPathFactory xpathFactory = XPathFactory.newInstance();

			
			// Create XPath object
			xpath = xpathFactory.newXPath();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	


	public List<String> getXpathValues(String xpathString) {
		List<String> list = new ArrayList<>();
		try {
			// create XPathExpression object
			xpath.setNamespaceContext(new UniversalNamespaceResolver(doc));
			XPathExpression expr = xpath.compile(xpathString);

			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				list.add(nodes.item(i).getTextContent());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	private class UniversalNamespaceResolver implements javax.xml.namespace.NamespaceContext {
	    private Document sourceDocument;
	    
	    Map<String, String> ns = new HashMap<>();
	 
	    public UniversalNamespaceResolver(Document document) {
	        sourceDocument = document;
	        findNamespaces(sourceDocument);
	        

	    }
	 
	    public String getNamespaceURI(String prefix) {
	        if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
	            return sourceDocument.lookupNamespaceURI(null);
	        }
	        else if(ns.containsKey(prefix)){
	        	return ns.get(prefix);	        }
	        else {
	            return sourceDocument.lookupNamespaceURI(prefix);
	        }
	    }
	 
	    public String getPrefix(String namespaceURI) {
	        return sourceDocument.lookupPrefix(namespaceURI);
	    }

		@Override
		public java.util.Iterator getPrefixes(String namespaceURI) {
			return null;
		}
		
		private void findNamespaces(Node node) {
			NamedNodeMap atts = node.getAttributes();
			if(atts!=null && atts.getLength()>0) {
				for(int i=0;i<atts.getLength();i++) {
					Node attribute=atts.item(i);
					String name=attribute.getNodeName();
					if(name.startsWith("xmlns:")) {
						name=name.substring("xmlns:".length());
						ns.put(name, attribute.getNodeValue());
					}
				}
			}
			for(int i=0;i<node.getChildNodes().getLength();i++) {
				Node node1=null;
				node1 = node.getChildNodes().item(i);
				findNamespaces(node1);
			}
		}
	 
	}

	
}


