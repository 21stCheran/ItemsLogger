package com.twentyonec.ItemsLogger.utils;

import net.md_5.bungee.api.ChatColor;

public enum Messages {
	
	PREFIX("&8[&cItemsLogger&8]"),
	
	//command messages
	NO_PERMISSION("&e You do not have the {0} permission node to execute this command."),
	NON_PLAYER("&e Only players may execute this command."),
	OFFLINE_PLAYER("&e {0} is not online."),
	
	CONFIG_RELOADED("&e Config reloaded."),
	
	UNSPECIFIED_ARGS("&e Must specify args"),
	INVALID_ARGS("&e Invalid arguments."),
	
	//ItemPlayer info messages
	NAME("&9Name"),
	DATE("&9Date"),
	CAUSE("&9Cause"),
	LOCATION("&9Location"),
	EXPERIENCE("&9Experience"),
	
	//String constructs
	NEW("\n"),
	
	//Help
	HELP_SEARCH("&7 /itemslogger [player] <date> <cause> <index> &eto search through logs."),
	HELP_RELOAD("&7 /itemslogger reload &eto reload config."),
	HELP_OPEN("&7 /openitemslog [player] [view|open] [date] [time] &eto view/open specific log");
	;
	
	private final String message;

    Messages(final String message) {
        this.message = message;
    }

    public String formatMessage(Object... args) {
        String colourFormattedMsg = this.getColourFormat();

        if (args == null || args.length == 0)
            return colourFormattedMsg;

        for (int i = 0; i < args.length; i++) {
            colourFormattedMsg = colourFormattedMsg.replace("{" + i + "}", args[i].toString());
        }

        return colourFormattedMsg;
    }

    private String getColourFormat() {
        return ChatColor.translateAlternateColorCodes('&', getMessage());
    }

    private String getMessage() {
        return this.message;
    }
	
	public static void main(String[] args) {
		System.out.println(Messages.PREFIX.getMessage() + Messages.CONFIG_RELOADED.getMessage());
	}

}
