package de.jlandsmann.discordTeamGenerator;

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

    public static int getIntFromString(String v, int defaultValue) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @SubscribeEvent
    public void onMessage(GuildMessageReceivedEvent event) {
        final var message = event.getMessage();
        final var rawMessage = message.getContentRaw();
        final var command = rawMessage.split(" ", -1);
        switch (command[0].toLowerCase()) {
            case "-generate-teams":
                var teamCount = 2;
                if (command.length >= 2) {
                    teamCount = getIntFromString(command[1], 2);
                }
                this.generateTeams(message, teamCount);
        }
    }

    private void generateTeams(Message message, int teamCount) {
        final var channel = this.getVoiceChannelOfMessageSender(message);
        final var members = this.getUsersOfChannel(channel);
        final var teams = this.getTeamsFromMembers(members, teamCount);
        final var replyMessage = this.transformTeamsToMessageEmbed(teams);
        final var messageAction = message.reply(replyMessage);
        messageAction.queue();
    }

    private VoiceChannel getVoiceChannelOfMessageSender(Message message) {
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

    private List<Member> getUsersOfChannel(VoiceChannel channel) {
        final var members = new ArrayList<>(channel.getMembers());
        members.removeIf(m -> m.getUser().isBot());
        return members;
    }

    private List<List<Member>> getTeamsFromMembers(List<Member> members, int teamCount) {
        final var teams = this.initEmptyTeams(teamCount);
        this.fillTeamsWithMembers(teams, members);

        return teams;
    }

    private List<List<Member>> initEmptyTeams(int teamCount) {
        List<List<Member>> teams = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) {
            teams.add(new ArrayList<>());
        }
        return teams;
    }

    private void fillTeamsWithMembers(List<List<Member>> teams, List<Member> originalMembers) {
        final var members = new ArrayList<>(originalMembers);
        Collections.shuffle(members);
        final int teamCount = teams.size();
        int teamIndex = 0;
        while (!members.isEmpty()) {
            final var member = members.remove(0);
            final var team = teams.get(teamIndex % teamCount);
            team.add(member);
            teamIndex++;
        }
    }

    private MessageEmbed transformTeamsToMessageEmbed(List<List<Member>> teams) {
        final var embedBuilder = new EmbedBuilder();
        final var teamNames = NameProvider.getNames();
        Collections.shuffle(teamNames);
        for (final var team : teams) {
            final var teamName = ListUtils.getNext(teamNames);
            final var field = this.createFieldForTeam(teamName, team);
            embedBuilder.addField(field);
        }
        return embedBuilder.build();
    }

    private MessageEmbed.Field createFieldForTeam(String teamName, List<Member> team) {
        final var members = this.transformMembersToString(team);
        return new MessageEmbed.Field(teamName, members, true);
    }

    private String transformMembersToString(List<Member> members) {
        if (members.isEmpty()) {
            return "Keine Mitglieder";
        }
        final var stringBuilder = new StringBuilder();
        for (Member member : members) {
            stringBuilder.append(member.getAsMention());
            stringBuilder.append("\n");
        }
        final var result = stringBuilder.toString();
        return result.substring(0, result.length() - 1);
    }
}
