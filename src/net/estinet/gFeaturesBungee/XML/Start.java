package net.estinet.gFeaturesBungee.XML;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Start {
	public void start(File f){
		  try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("mail");
				doc.appendChild(rootElement);

				// staff elements
				Element staff = doc.createElement("player");
				rootElement.appendChild(staff);

				// set attribute to staff element
				Attr attr = doc.createAttribute("uuid");
				attr.setValue("154870954353049");
				staff.setAttributeNode(attr);

				// shorten way
				// staff.setAttribute("id", "1");

				// firstname elements
				Element firstname = doc.createElement("anotheruuid");
				firstname.appendChild(doc.createTextNode("u stink"));
				staff.appendChild(firstname);

				// lastname elements
				Element lastname = doc.createElement("andanotheruuid");
				lastname.appendChild(doc.createTextNode("nah man"));
				staff.appendChild(lastname);

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(f);

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);

				System.out.println("File saved!");

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
	}
}
