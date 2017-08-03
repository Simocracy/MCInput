# MCInput
IRC Bot connection for Spigot Server for Minecraft for simple IRC<->Minecraft chat bridge and online player list.

### Ingame Commands
`/mci <message>`
Displays a message in ingame chat. Usefull for IRC -> MC. The Bot can write /mci message to put an IRC message into ingame chat. Only can used from console.

`/irclist`
Displays the current online IRC users.
Permissions: mcinput.irc

`/mcir`
Reloads the config.
Permission: mcinput.admin

`/mcilist`
Updates the Minecraft online players list.
Permission: mcinput.irc

### Default Config
```
meldungen:
    nurKonsole: Kann nur von Konsole verwendet werden.
    ircList:
        nachricht: Im IRC online sind folgende %a Personen
        dateifehler: Konnte IRC-Nickliste nicht auslesen :(
        keinerDa: Keine Sau ist im IRC... :(
    gameList:
        nachricht: MC-Nickliste erfolgreich aktualisiert.
        dateifehler: Konnte MC-Nickliste nicht schreiben :(

dateien:
    ircOn: ircon.txt
    gameOn: gameon.txt

umlaute:
    ae: aaee
    oe: ooee
    ue: uuee
    Ae: AAee
    Oe: OOee
    Ue: UUee

ircBotVerbindung:
    ipAdresse: 127.0.0.1
    port: 45678
```

Details:
`meldungen`
Configures the ingame messages.
`nurKonsole`
Command can be used only from terminal.
`ircList.nachricht`
Default message if users are in IRC on `/irclist`.
`ircList.dateifehler`
Error message if IRC online list could not readed.
`ircList.keinerDa`
Message if nobody is in IRC channel.
`gameList.nachricht`
Default message after updating Minecraft online list.
`gameList.dateifehler`
Error message if Minecraft online list could not updated.

`dateien`
Configures the used files for the current online player lists in IRC and Minecraft. Must be readed/writed from IRC bot to display the current online users.

`umlaute`
Configures the conversion of german umlaute.

`ircBotVerbindung`
Configures the connection to the IRC bot.