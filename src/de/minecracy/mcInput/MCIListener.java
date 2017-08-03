package de.minecracy.mcInput;

import java.io.IOException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MCIListener implements Listener {
	
	private MCIDateiverwaltung dateiVerw;
	private MCInputBotVerbindung botVerb;
	
	public MCIListener(MCIDateiverwaltung dateiVerw, MCInputBotVerbindung botVerb) {
		this.dateiVerw = dateiVerw;
		this.botVerb = botVerb;
	}
	
	@EventHandler
	public void normalLogin(PlayerLoginEvent e) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					spielerAktualisieren();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 500);

		String nick = e.getPlayer().getName();
		String host = e.getAddress().getHostAddress();
		String world = e.getPlayer().getWorld().getName();
		double locX = e.getPlayer().getLocation().getX();
		double locY = e.getPlayer().getLocation().getY();
		double locZ = e.getPlayer().getLocation().getZ();

		String sendToBot =  "JOIN " + nick + " " + host + " " + world + " " + locX + " " + locY + " " + locZ;
		botVerb.botVerbindungAufbauen();
		botVerb.sendBotMess(sendToBot);
		botVerb.botVerbindungTrennen();
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent pqe) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					spielerAktualisieren();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 500);

		String nick = pqe.getPlayer().getName();
		String reason = pqe.getQuitMessage();
		
		String sendToBot =  "QUIT " + nick + " " + reason;
		botVerb.botVerbindungAufbauen();
		botVerb.sendBotMess(sendToBot);
		botVerb.botVerbindungTrennen();
	}
	
	@EventHandler
	public void chatMessage(AsyncPlayerChatEvent event) {
		String sendToBot = "";
		
//		if(event.getMessage().startsWith("/me")) {
//			String nick = event.getPlayer().getName();
//			String text = event.getMessage();
//			sendToBot = "ACTION " + nick + " " + text;
//		}
//		
//		else if(event.getMessage().startsWith("/")) {
//			String nick = event.getPlayer().getName();
//			String cmd = event.getMessage();
//			sendToBot = "CMD " + nick + " " + cmd;
//		}
//		
//		else {
			String nick = event.getPlayer().getName();
			String msg = event.getMessage();
			sendToBot = "MSG " + nick + " " + msg;
//		}
		
		if(!sendToBot.trim().isEmpty()) {
			botVerb.botVerbindungAufbauen();
			botVerb.sendBotMess(sendToBot);
			botVerb.botVerbindungTrennen();
		}
	}
	
	public void spielerAktualisieren() throws IOException {
		Collection<? extends Player> onSpielerCol = Bukkit.getOnlinePlayers();
		Player[] onSpieler = onSpielerCol.toArray(new Player[onSpielerCol.size()]);
		String onliste = "";
		
		if(onSpieler.length > 1) {
			for(int i = 0; i < onSpieler.length-1; i++) {
				onliste += onSpieler[i].getPlayerListName() + " ";
				
//				mci.log.info("[MCI]" + onliste + i);
			}
			onliste += onSpieler[onSpieler.length-1].getPlayerListName();
//			mci.log.info("[MCI]" + onliste + 0);
		}
		
		if(onSpieler.length == 1) {
			onliste = onSpieler[0].getPlayerListName();
		}
		
//		mci.log.info("[MCI]" + onliste + "|");
		
		dateiVerw.schreibeGameNicks(onliste);

	}
}
