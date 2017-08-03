package de.minecracy.mcInput;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class MCInputBotVerbindung {

	protected boolean isBotVerbindungAufgebaut;
	private Socket botSocket;
	private byte[] ipAdresse;
	private int port;
//	private MCIDateiverwaltung datVer;
	
	public MCInputBotVerbindung(MCInput mci, String ip, int port, MCIDateiverwaltung datVer) {
//		this.datVer = datVer;
		String[] adressBestandteile = ip.split("\\.");
//		datVer.mCIExLogger(String.valueOf(adressBestandteile.length));
		if(!(adressBestandteile.length == 4 || adressBestandteile.length == 16))
			mci.log.warning("Keine Verbindung zum Bot möglich!");
		
		ipAdresse = new byte[adressBestandteile.length];
		for(int i = 0; i < adressBestandteile.length; i++) {
//			datVer.mCIExLogger(String.valueOf(adressBestandteile[i]));
			int zahl = Integer.parseInt(adressBestandteile[i]);
			ipAdresse[i] = (byte) zahl;
		}
		
		this.port = port;
	}
	
	protected boolean botVerbindungAufbauen() {
		try {
			botSocket = new Socket(InetAddress.getByAddress(ipAdresse) , port);
			isBotVerbindungAufgebaut = true;
			
//			datVer.mCIExLogger("Verbunden");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			datVer.mCIExLogger(e);
			e.printStackTrace();
			return false;
		}
	}
	
	protected boolean botVerbindungTrennen() {
		try {
			isBotVerbindungAufgebaut = false;
			if(isBotVerbindungAufgebaut)
				botSocket.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	protected boolean sendBotMess(String mess) {
		try {
			if(isBotVerbindungAufgebaut) {
				
				PrintWriter out = new PrintWriter(botSocket.getOutputStream(), true);
				out.println(mess);
				
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
