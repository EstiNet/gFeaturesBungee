package net.estinet.gFeaturesBungee.EstiMail;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.estinet.gFeaturesBungee.MojangAPI.UUIDFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Gloze {
	@SuppressWarnings("deprecation")
	public static String read(ProxiedPlayer p){
		String finali = "";
		try{
		File fXmlFile = new File("/Users/mkyong/staff.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		NodeList first = doc.getFirstChild().getChildNodes();
		for(int i = 0; i != first.getLength(); i++){
			if(first.item(i).getAttributes().getNamedItem("uuid").equals(p.getUUID())){
				for(int it = 0; it!= first.item(i).getChildNodes().getLength(); it++){
					Node nod = first.item(i).getChildNodes().item(it);
					finali += ChatColor.WHITE + nod.getNodeName() + ": " + nod.getTextContent();
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(finali.equals("")){
			return ChatColor.DARK_AQUA + "You have no mail. :(";
		}
		return finali;
	}
	@SuppressWarnings("deprecation")
	public static void clear(ProxiedPlayer p){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			NodeList players = doc.getFirstChild().getChildNodes();
			for(int i = 0; i != players.getLength(); i++){
				if(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(p.getUUID())){
					doc.getFirstChild().removeChild(players.item(i));
					Element staff = doc.createElement("player");
					doc.getFirstChild().appendChild(staff);			
					Attr attr = doc.createAttribute("uuid");
					attr.setValue(p.getUUID());
					staff.setAttributeNode(attr);
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
	@SuppressWarnings("deprecation")
	public static void send(ProxiedPlayer p, String send, String message){

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			NodeList players = doc.getFirstChild().getChildNodes();
			for(int i = 0; i != players.getLength(); i++){
				if(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(UUIDFetcher.getUUIDFromName(send, false))){
					Element lastname = doc.createElement(p.getUUID());
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
		try{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

		NodeList players = doc.getFirstChild().getChildNodes();
		for(int i = 0; i != players.getLength(); i++){
			BungeeCord.getInstance().getLogger().info(UUIDFetcher.getUUIDFromName(send, false));
			BungeeCord.getInstance().getLogger().info(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue());
			if(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(UUIDFetcher.getUUIDFromName(send, false))){
				return true;
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
