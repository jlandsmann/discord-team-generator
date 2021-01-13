package de.jlandsmann.discordTeamGenerator;

import de.jlandsmann.discordTeamGenerator.utils.ListUtils;
import de.jlandsmann.discordTeamGenerator.utils.NameProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamGenerator {

    private static final int DEFAULT_TEAM_COUNT = 2;

    public static MessageEmbed generateTeams(List<Member> members, Integer teamCount) {
        if (teamCount == null) {
            teamCount = DEFAULT_TEAM_COUNT;
        }
        final var teams = getTeamsFromMembers(members, teamCount);
        return transformTeamsToMessageEmbed(teams);
    }

    private static List<List<Member>> getTeamsFromMembers(List<Member> members, int teamCount) {
        return ListUtils.split(members, teamCount);
    }


    private static MessageEmbed transformTeamsToMessageEmbed(List<List<Member>> teams) {
        final var embedBuilder = new EmbedBuilder();
        final var teamNames = NameProvider.getNames();
        Collections.shuffle(teamNames);
        for (final var team : teams) {
            final var teamName = ListUtils.getNext(teamNames);
            final var field = createFieldForTeam(teamName, team);
            embedBuilder.addField(field);
        }
        return embedBuilder.build();
    }

    private static MessageEmbed.Field createFieldForTeam(String teamName, List<Member> team) {
        final var members = transformMembersToString(team);
        return new MessageEmbed.Field(teamName, members, true);
    }

    private static String transformMembersToString(List<Member> members) {
        if (members.isEmpty()) {
            return "*Keine Mitglieder*";
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
