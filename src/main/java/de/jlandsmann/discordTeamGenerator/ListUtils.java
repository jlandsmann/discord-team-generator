package de.jlandsmann.discordTeamGenerator;

import java.util.List;

public class ListUtils {

    public static <T> T getNext(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        final var idx = (int) (Math.random() * list.size());
        return list.remove(idx);
    }


}
