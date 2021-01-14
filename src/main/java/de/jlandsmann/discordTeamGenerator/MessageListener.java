package de.jlandsmann.discordTeamGenerator;

import de.jlandsmann.discordTeamGenerator.utils.CommandUtils;
import de.jlandsmann.discordTeamGenerator.utils.ListUtils;
import de.jlandsmann.discordTeamGenerator.utils.MessageUtils;
import de.jlandsmann.discordTeamGenerator.utils.NameProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageListener {

    @SubscribeEvent
    public void onMessage(GuildMessageReceivedEvent event) {
        final var message = event.getMessage();
        final var command = CommandUtils.getCommandFromMessage(message);
        final var argument = CommandUtils.getCommandArgumentFromMessage(message);

        switch (command.toLowerCase()) {
            case "-generate-teams":
                Integer teamCount = CommandUtils.getIntFromString(argument);
                this.generateTeams(message, teamCount);
        }
    }

    private void generateTeams(Message message, Integer teamCount) {
        final var voiceChannel = MessageUtils.getVoiceChannelOfMessageSender(message);
        final var users = MessageUtils.getUsersOfChannel(voiceChannel);
        final var response = MemberTeamGenerator.generateTeams(users, teamCount);
        final var responseAction = message.reply(response);
        responseAction.queue();
    }
}
