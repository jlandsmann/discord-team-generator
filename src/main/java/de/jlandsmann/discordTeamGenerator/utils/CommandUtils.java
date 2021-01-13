package de.jlandsmann.discordTeamGenerator.utils;

import net.dv8tion.jda.api.entities.Message;

public class CommandUtils {

    public static Integer getIntFromString(String v) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCommandFromMessage(Message message) {
        final var rawMessage = message.getContentRaw();
        final var commandParts = rawMessage.split(" ", -1);
        return commandParts[0];
    }

    public static String getCommandArgumentFromMessage(Message message) {
        final var rawMessage = message.getContentRaw();
        final var commandParts = rawMessage.split(" ", -1);
        String argument = null;
        if (commandParts.length > 1) {
            argument = commandParts[1];
        }
        return argument;
    }
}
