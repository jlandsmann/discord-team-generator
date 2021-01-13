package de.jlandsmann.discordTeamGenerator.utils;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    public static <T> T getNext(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        final var idx = (int) (Math.random() * list.size());
        return list.remove(idx);
    }

    public static <T> List<List<T>> split(List<T> items, int chunkCount) {
        List<List<T>> chunks = initChunks(chunkCount);
        fillChunks(chunks, items);
        return chunks;
    }

    private static <T> List<List<T>> initChunks(int chunkCount) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < chunkCount; i++) {
            chunks.add(new ArrayList<>());
        }
        return chunks;
    }

    private static <T> void fillChunks(List<List<T>> chunks, List<T> originalItems) {
        final var items = new ArrayList<>(originalItems);
        Collections.shuffle(items);
        final int listSize = items.size();
        int chunkIndex = 0;
        while (!items.isEmpty()) {
            final var item = items.remove(0);
            final var chunk = chunks.get(chunkIndex % listSize);
            chunk.add(item);
            chunkIndex++;
        }
    }


}
