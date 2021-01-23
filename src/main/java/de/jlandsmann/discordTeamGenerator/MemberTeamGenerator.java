package de.jlandsmann.discordTeamGenerator;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class MemberTeamGenerator extends TeamGenerator<Member> {

    public static MessageEmbed generateTeams(List<Member> members, Integer teamCount) {
        TeamGenerator<Member> generator = new MemberTeamGenerator(members, teamCount);
        return generator.generateWithResponse();
    }

    protected MemberTeamGenerator(List<Member> members, Integer teamCount) {
        super(members, teamCount);
    }

    @Override
    protected String transformMemberToString(Member item) {
        return item.getAsMention();
    }
}
