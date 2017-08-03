package de.minecracy.mcInput;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MCInput extends JavaPlugin {
	
	protected Logger log = Logger.getLogger("Minecraft");
	private MCInputCommandExecutor executor;
	private MCIDateiverwaltung dateiVerw;
	private MCIListener playerListener;
	private MCInputBotVerbindung botVerb;
	
	protected String errorNachrichtMCI;
	protected String ircListNachricht;
	protected String ircListDateifehler;
	protected String ircNicklisteDatei;
	protected String ircKeinerDa;
	protected String gameListNachricht;
	protected String gameNicklisteDatei;
	protected String gameListDateifehler;
	
	protected String umlautae;
	protected String umlautoe;
	protected String umlautue;
	protected String umlautAe;
	protected String umlautOe;
	protected String umlautUe;
	
	protected String ircBotAdresse;
	protected int ircBotPort;
	
	public void onEnable() {
		getConfig();
//		defaultEinstellungen();
		einstellungenLaden();
//		legeConfigAn();
		
		dateiVerw = new MCIDateiverwaltung(this);
		botVerb = new MCInputBotVerbindung(this, ircBotAdresse, ircBotPort, dateiVerw);
		playerListener = new MCIListener(dateiVerw, botVerb);
		executor = new MCInputCommandExecutor(this, dateiVerw, playerListener, botVerb);
		
		getServer().getPluginManager().registerEvents(playerListener, this);
		
		getCommand("mci").setExecutor(executor);
		getCommand("irclist").setExecutor(executor);
		getCommand("mcir").setExecutor(executor);
		getCommand("mcilist").setExecutor(executor);
		
		log.info("[MCI] MCInput geladen.");
		
		try {
			playerListener.spielerAktualisieren();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onDisable() {
		try {
			playerListener.spielerAktualisieren();
			botVerb.botVerbindungTrennen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info("[MCI] MCInput deaktiviert.");
	}

	public void configNeuladen(CommandSender sender) {
		log.info("[MCI] MCInput-Einstellungen werden neu geladen.");
		this.reloadConfig();
		einstellungenLaden();
		sender.sendMessage("Einstellungen neu geladen.");
	}
	
//	private void legeConfigAn() {
//		File config = new File(getDataFolder(), "config.yml");
//
//		if(!config.exists()) {
//			try {
//				getConfig().save(config);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
//	private void defaultEinstellungen() {
//		getConfig().addDefault("meldungen.nurKonsole", "Kann nur von Konsole verwendet werden.");
//		
//		getConfig().addDefault("meldungen.ircList.nachricht", "Im IRC online sind folgende %a Personen");
//		getConfig().addDefault("meldungen.ircList.dateifehler", "Konnte IRC-Nickliste nicht auslesen :(");
//		getConfig().addDefault("meldungen.ircList.keinerDa", "Keine Sau ist im IRC... :(");
//		
//		getConfig().addDefault("meldungen.gameList.nachricht", "MC-Nickliste erfolgreich aktualisiert.");
//		getConfig().addDefault("meldungen.gameList.dateifehler", "Konnte MC-Nickliste nicht schreiben :(");
//		
//		getConfig().addDefault("dateien.ircOn", "ircon.txt");
//		getConfig().addDefault("dateien.gameOn", "gameon.txt");
//		
//		getConfig().addDefault("umlaute.ae", "aaee");
//		getConfig().addDefault("umlaute.oe", "ooee");
//		getConfig().addDefault("umlaute.ue", "uuee");
//		getConfig().addDefault("umlaute.Ae", "AAee");
//		getConfig().addDefault("umlaute.Oe", "OOee");
//		getConfig().addDefault("umlaute.Ue", "UUee");
//		
//		getConfig().addDefault("permissions.erhalten", "mcinput.erhalten");
//		getConfig().addDefault("permissions.irclist", "mcinput.irclist");
//		getConfig().addDefault("permissions.reload", "mcinput.reload");
//	}
	
	private void einstellungenLaden() {
		errorNachrichtMCI = getConfig().getString("meldungen.nurKonsole");
		
		ircListNachricht = getConfig().getString("meldungen.ircList.nachricht");
		ircListDateifehler = getConfig().getString("meldungen.ircList.dateifehler");
		ircKeinerDa = getConfig().getString("meldungen.ircList.keinerDa");
		
		gameListNachricht = getConfig().getString("meldungen.gameList.nachricht");
		gameListDateifehler = getConfig().getString("meldungen.gameList.dateifehler");
		
		ircNicklisteDatei = getConfig().getString("dateien.ircOn");
		gameNicklisteDatei = getConfig().getString("dateien.gameOn");
		
		umlautae = getConfig().getString("umlaute.ae");
		umlautoe = getConfig().getString("umlaute.oe");
		umlautue = getConfig().getString("umlaute.ue");
		umlautAe = getConfig().getString("umlaute.Ae");
		umlautOe = getConfig().getString("umlaute.Oe");
		umlautUe = getConfig().getString("umlaute.Ue");
		
		ircBotAdresse = getConfig().getString("ircBotVerbindung.ipAdresse");
		ircBotPort = getConfig().getInt("ircBotVerbindung.port");
	}
}
