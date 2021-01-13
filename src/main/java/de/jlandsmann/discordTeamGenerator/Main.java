package de.jlandsmann.discordTeamGenerator;

import de.jlandsmann.discordTeamGenerator.utils.PropertiesReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.apache.log4j.BasicConfigurator;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        final var jda = initJDA();
        jda.setEventManager(new AnnotatedEventManager());
        jda.addEventListener(new MessageListener());
        System.out.println("Done");
    }

    private static JDA initJDA() {
        BasicConfigurator.configure();
        final var token = PropertiesReader.getToken();
        final var jdaBuilder = JDABuilder.createDefault(token);
        try {
            final var jda = jdaBuilder.build();
            return jda;
        } catch (LoginException e) {
            throw new IllegalStateException(e);
        }

    }
}
