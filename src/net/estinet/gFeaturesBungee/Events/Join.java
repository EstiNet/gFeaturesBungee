package net.estinet.gFeaturesBungee.Events;

import java.io.File;
import java.io.IOException;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;

public class Join implements Listener{
	public void onPostLogin(PostLoginEvent event){
			try{
					String filepath = "plugins/gFeatures/mail.xml";
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(filepath);

					NodeList list = doc.getElementsByTagName("player");

					for(int i = 0; i != list.getLength(); i++){
						Node node = list.item(i);
						if(node.getAttributes().getNamedItem("uuid").equals(event.getPlayer().getUUID())){
							//SHOW HOW MANY EMAILS
							return;
						}
					}
					otherwise(event);

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (SAXException sae) {
					sae.printStackTrace();
				}
	}
	public void otherwise(PostLoginEvent event){

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			// staff elements
			Element staff = doc.createElement("player");
			doc.getFirstChild().appendChild(staff);

			// set attribute to staff element
			Attr attr = doc.createAttribute("uuid");
			attr.setValue(event.getPlayer().getUUID());
			staff.setAttributeNode(attr);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("plugins/gFeatures/mail.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
			catch(Exception e){
				e.printStackTrace();
			}
	}
}