package net.estinet.gFeaturesBungee.EstiMail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.estinet.gFeaturesBungee.MojangAPI.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Gloze {
	public static void clear(ProxiedPlayer p){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			NodeList players = doc.getFirstChild().getChildNodes();
			for(int i = 0; i != players.getLength(); i++){
				if(players.item(i).getAttributes().getNamedItem("uuid").equals(p.getUUID())){
					
				}
			}

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
	public static void send(CommandSender p, String send, String message){

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			NodeList players = doc.getFirstChild().getChildNodes();
			for(int i = 0; i != players.getLength(); i++){
				List<String> names = new ArrayList<>();
				names.add(p.getName());
				UUIDFetcher uuids = new UUIDFetcher(names);
				if(players.item(i).getAttributes().getNamedItem("uuid").equals(uuids.call().get(uuids).toString())){
					List<String> name = new ArrayList<>();
					name.add(p.getName());
					UUIDFetcher uuid = new UUIDFetcher(name);
					Element lastname = doc.createElement(uuid.call().get(name).toString());
					lastname.appendChild(doc.createTextNode(message));
					players.item(i).appendChild(lastname);
				}
			}

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
	public static boolean check(String send){
		return false;
	}
}
