package net.estinet.gFeaturesBungee.EstiMail;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

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

import net.estinet.gFeaturesBungee.MojangAPI.NameFetcher;
import net.estinet.gFeaturesBungee.MojangAPI.UUIDFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Gloze {
	@SuppressWarnings("deprecation")
	public static String read(ProxiedPlayer p){
		String finali = "";
		try{
		File fXmlFile = new File("plugins/gFeatures/mail.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		NodeList first = doc.getFirstChild().getChildNodes();
		for(int i = 0; i != first.getLength(); i++){
			if(first.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(p.getUUID())){
				for(int it = 0; it != first.item(i).getChildNodes().getLength(); it++){
					Node nod = first.item(i).getChildNodes().item(it);
					String hex =  nod.getAttributes().getNamedItem("uuid").getNodeValue().replaceFirst( "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5" );
					NameFetcher nf = new NameFetcher(Arrays.asList(UUID.fromString(hex)));
					finali += ChatColor.GOLD + nf.call().get(hex) + ": " + ChatColor.AQUA + nod.getTextContent() + "\n";
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(finali.equals("")){
			return ChatColor.BOLD + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "EstiMail" + ChatColor.WHITE + "" + ChatColor.BOLD + "] " + ChatColor.DARK_AQUA + "You have no mail. :(";
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
		BungeeCord.getInstance().getPluginManager().getPlugin("gFeatures").getProxy().getScheduler().runAsync(BungeeCord.getInstance().getPluginManager().getPlugin("gFeatures"), new Runnable() {
			public void run(){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

			NodeList players = doc.getFirstChild().getChildNodes();
			for(int i = 0; i != players.getLength(); i++){
				if(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(UUIDFetcher.getUUIDOf(send).toString())){
					Element lastname = doc.createElement("mesail");
					Attr attr = doc.createAttribute("uuid");
					attr.setValue(p.getUUID());
					lastname.setAttributeNode(attr);
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
		});
	}
	public static boolean check(String send){
		try{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.parse(new File("plugins/gFeatures/mail.xml"));

		NodeList players = doc.getFirstChild().getChildNodes();
		for(int i = 0; i != players.getLength(); i++){
			if(players.item(i).getAttributes().getNamedItem("uuid").getNodeValue().equals(UUIDFetcher.getUUIDOf(send).toString())){
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
