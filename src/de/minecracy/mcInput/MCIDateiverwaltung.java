package de.minecracy.mcInput;

import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;

public class MCIDateiverwaltung {
	
	private MCInput mci;
	
	public MCIDateiverwaltung(MCInput mci) {
		this.mci = mci;
	}
	
//	final static private SimpleDateFormat ausgabeDatum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	protected void mCIExLogger(Exception ausgabe){
//		try {
//			File outputFile = new File("D:/Eigene Dateien/Minecraft/Server/plugins/MCInput/MCI.log");
//			if(!outputFile.exists()) outputFile.createNewFile();
//			PrintWriter schreiben = new PrintWriter(new FileWriter(outputFile, true),true);
//			schreiben.print(ausgabeDatum.format(new Date()) + ": ");
//			ausgabe.printStackTrace(schreiben);
//			schreiben.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	protected void mCIExLogger(String ausgabe){
//		try {
//			File outputFile = new File("D:/Eigene Dateien/Minecraft/Server/plugins/MCInput/MCI.log");
//			if(!outputFile.exists()) outputFile.createNewFile();
//			PrintWriter schreiben = new PrintWriter(new FileWriter(outputFile, true),true);
//			schreiben.print(ausgabeDatum.format(new Date()) + ": ");
//			schreiben.println(ausgabe);
//			schreiben.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public String leseIrcNicks() throws IOException {
		File nicklisteDatei = new File(mci.getDataFolder(), mci.ircNicklisteDatei);
		
		if(!nicklisteDatei.exists()) nicklisteDatei.createNewFile();
		
		FileInputStream inStream = new FileInputStream(nicklisteDatei);
		
		String liste = "";
		byte zeichen;
		
		do {
			zeichen = (byte) inStream.read();
			liste += (char) zeichen;
		} while(zeichen != -1);
		
		inStream.close();
		
		return liste;
	}
	
	public void schreibeGameNicks(String nicks) throws IOException {
		File nicklisteDatei = new File(mci.getDataFolder(), mci.gameNicklisteDatei);
		
		if(!nicklisteDatei.exists()) nicklisteDatei.createNewFile();
		
		FileOutputStream outStream = new FileOutputStream(nicklisteDatei);
		
		outStream.write(nicks.getBytes());
		
		outStream.close();
	}
}
