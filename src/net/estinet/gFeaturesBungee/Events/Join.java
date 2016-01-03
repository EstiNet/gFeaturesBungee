package net.estinet.gFeaturesBungee.Events;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;

public class Join implements Listener{
	public void onPostLogin(PostLoginEvent event){
		Thread thr = new Thread(new Runnable(){
			public void run(){
				try {
					String filepath = "plugins/gFeatures/mail.xml";
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(filepath);

					NodeList list = doc.getElementsByTagName("staff");

					System.out.println("Total of elements : " + list.getLength());

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (SAXException sae) {
					sae.printStackTrace();
				}
			}
		});
		thr.start();
	}
}