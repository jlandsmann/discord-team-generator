package de.jlandsmann.discordTeamGenerator.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    public static VoiceChannel getVoiceChannelOfMessageSender(Message message) {
        final var commander = message.getMember();
        final var voiceState = commander.getVoiceState();
        if (voiceState == null) {
            throw new IllegalStateException();
        }
        final var channel = voiceState.getChannel();
        if (channel == null) {
            throw new IllegalStateException();
        }

        return channel;
    }

    public static List<Member> getUsersOfChannel(VoiceChannel channel) {
        final var members = new ArrayList<>(channel.getMembers());
        members.removeIf(m -> m.getUser().isBot());
        return members;
    }
}
