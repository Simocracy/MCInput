package de.minecracy.mcInput;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MCInputCommandExecutor implements CommandExecutor  {
	
	private MCInput mci;
	private MCIDateiverwaltung dateiVerw;
	private MCIListener playerListener;
//	private MCInputBotVerbindung botVerb;
	
	public MCInputCommandExecutor(MCInput mci, MCIDateiverwaltung dateiVerw,
			MCIListener playerListener, MCInputBotVerbindung botVerb) {
		this.mci = mci;
		this.dateiVerw = dateiVerw;
		this.playerListener = playerListener;
//		this.botVerb = botVerb;
	}
	
	void ausgabe(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage(mci.errorNachrichtMCI);
		} else {
			String ausgabe = "";
			for(int i = 0; i < args.length -1; i++) {
//				try {
//					ausgabe += new String(args[i].getBytes("cp1252"), "utf8") + " ";
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				ausgabe += args[i] + " ";
			}
			ausgabe += args[args.length-1];
			ausgabe(umlaute(ausgabe));
		}
	}
	
	public String umlaute(String text) {
		text = text.replaceAll(mci.umlautae, "ä");
		text = text.replaceAll(mci.umlautoe, "ö");
		text = text.replaceAll(mci.umlautue, "ü");
		text = text.replaceAll(mci.umlautAe, "Ä");
		text = text.replaceAll(mci.umlautOe, "Ö");
		text = text.replaceAll(mci.umlautUe, "Ü");
		return text;
	}
	
	public void ausgabe(String nachricht) {
		ausgabe(nachricht, "mcinput.erhalten");
	}
	
	public void ausgabe(String nachricht, String permission) {
		Bukkit.broadcast(nachricht, permission);
	}
	
	void ircList(CommandSender sender) throws IOException {
		String liste = dateiVerw.leseIrcNicks();

		liste = liste.substring(0, liste.length()-1);
		String[] lSplit = liste.split(" ");
		
		int anzahlLeute = lSplit.length;
		
		String[] musterNachricht = mci.ircListNachricht.split("%a");
		
		String nachricht = ChatColor.WHITE + musterNachricht[0] + ChatColor.RED +
				anzahlLeute + ChatColor.WHITE + musterNachricht[1] + ": ";
		
		if(lSplit[0].length() > 0) {
			for(int i = 0; i < lSplit.length; i++) {
				if(i % 2 == 0) {
					nachricht += ChatColor.AQUA + lSplit[i] + ChatColor.WHITE + ", ";
				}
				else {
					nachricht += ChatColor.RED + lSplit[i] + ChatColor.WHITE + ", ";
				}
			}
			nachricht = nachricht.substring(0, nachricht.length()-2);
//			nachricht += ChatColor.AQUA + lSplit[lSplit.length-1];
		}
		else {
//			if(lSplit[0].length() > 0) {
//				nachricht += ChatColor.AQUA + lSplit[0];
//			}
//			else {
				nachricht = mci.ircKeinerDa;
//			}
		}
		
		sender.sendMessage(nachricht);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("mci")) {
			ausgabe(sender, args);
			return true;
		}
		
		else if(command.getName().equalsIgnoreCase("irclist")) {
			if(sender.hasPermission("mcinput.irc")) {
				final CommandSender cmdSender = sender;
				new Thread(new BukkitRunnable() {
					
					@Override
					public void run() {
						try {
							ircList(cmdSender);
						} catch (IOException e) {
							cmdSender.sendMessage(mci.ircListDateifehler);
							e.printStackTrace();
						}
					}
				}).start();
			}
			return true;
		}
		
		else if(command.getName().equalsIgnoreCase("mcir")) {
			if(sender.hasPermission("mcinput.admin")) {
				mci.configNeuladen(sender);
			}
			return true;
		}
		
		else if(command.getName().equalsIgnoreCase("mcilist")) {
			if(sender.hasPermission("mcinput.irc")) {
				final CommandSender cmdSender = sender;
				new Thread(new BukkitRunnable() {
					
					@Override
					public void run() {
						try {
							playerListener.spielerAktualisieren();
							cmdSender.sendMessage(mci.gameListNachricht);
						} catch (IOException e) {
							cmdSender.sendMessage(mci.gameListDateifehler);
							e.printStackTrace();
						}
					}
				}).start();
			}
			return true;
		}
		
//		else {
//			if(command.getName().equalsIgnoreCase("me")) {
//				final CommandSender cmdSender = sender;
//				final String[] cmdArgs = args;
//				new Thread(new BukkitRunnable() {
//					
//					@Override
//					public void run() {
//						String arguments = "";
//						for(int i = 0; i < cmdArgs.length; i++ ) {
//							arguments += cmdArgs[i] + " ";
//						}
//						String sendToBot = "ACTION " + cmdSender.getName() + " " + arguments.trim();
//						botVerb.botVerbindungAufbauen();
//						botVerb.sendBotMess(sendToBot);
//						botVerb.botVerbindungTrennen();
//					}
//				}).start();
//				
//				return true;
//			}
//		
//			else {
//				final CommandSender cmdSender = sender;
//				final String[] cmdArgs = args;
//				new Thread(new BukkitRunnable() {
//					
//					@Override
//					public void run() {
//						String arguments = "";
//						for(int i = 0; i < cmdArgs.length; i++ ) {
//							arguments += cmdArgs[i] + " ";
//						}
//						String sendToBot = "CMD " + cmdSender.getName() + " " + arguments.trim();
//						botVerb.botVerbindungAufbauen();
//						botVerb.sendBotMess(sendToBot);
//						botVerb.botVerbindungTrennen();
//					}
//				}).start();
//				return true;
//			}
//			
//		}
		
		return false;
	}

}
