/**
 * @author Ishan Goyal : 1056051
 *	This class is used to define the xml parsing and formatting operations.
 *
 */


import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class xml_parser {
	
		private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		private DocumentBuilder builder;  
		private Document doc;
		
		public xml_parser(String xml_string) {
		    try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}  
		    try {
				doc = builder.parse(new InputSource(new StringReader(xml_string)));
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
		}
		public boolean lookup_action(String action) {
			return doc.getElementsByTagName("action").item(0).getTextContent().contains(action);
		}
		public String get_element(String tag) {
			return doc.getElementsByTagName(tag).item(0).getTextContent();
		}
}
