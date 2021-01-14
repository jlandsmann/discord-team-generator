package de.jlandsmann.discordTeamGenerator;

import de.jlandsmann.discordTeamGenerator.utils.ListUtils;
import de.jlandsmann.discordTeamGenerator.utils.NameProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Collections;
import java.util.List;

public abstract class TeamGenerator<T> {

    protected static final int DEFAULT_TEAM_COUNT = 2;

    private final List<T> items;
    private final int teamCount;
    private final List<String> teamNames;

    protected TeamGenerator(List<T> items, Integer teamCount) {
        this.items = items;
        this.teamCount = (teamCount == null) ? DEFAULT_TEAM_COUNT : teamCount;
        this.teamNames = NameProvider.getNames();
        Collections.shuffle(this.teamNames);
    }

    public List<List<T>> generate() {
        return ListUtils.split(items, teamCount);
    }

    public MessageEmbed generateWithResponse() {
        List<List<T>> teams = this.generate();
        return generateMessageEmbedForReply(teams);
    }


    protected MessageEmbed generateMessageEmbedForReply(List<List<T>> teams) {
        final var embedBuilder = new EmbedBuilder();
        for (final var team : teams) {
            final var teamName = ListUtils.getNext(this.teamNames);
            final var field = createFieldForTeam(teamName, team);
            embedBuilder.addField(field);
        }
        return embedBuilder.build();
    }

    protected MessageEmbed.Field createFieldForTeam(String teamName, List<T> team) {
        final var members = transformMembersToString(team);
        return new MessageEmbed.Field(teamName, members, true);
    }

    protected String transformMembersToString(List<T> items) {
        if (items.isEmpty()) {
            return "*Keine Mitglieder*";
        }
        final var stringBuilder = new StringBuilder();
        for (T member : items) {
            final var name = transformMemberToString(member);
            stringBuilder.append(name);
            stringBuilder.append("\n");
        }
        final var result = stringBuilder.toString();
        return result.substring(0, result.length() - 1);
    }

    protected abstract String transformMemberToString(T item);
}
