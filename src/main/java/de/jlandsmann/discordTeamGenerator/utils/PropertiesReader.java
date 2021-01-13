package de.jlandsmann.discordTeamGenerator.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public static Properties getAllProperties(){
        try {
            Properties databaseProperties = new Properties();
            FileInputStream in = new FileInputStream("team-generator.properties");
            databaseProperties.load(in);
            in.close();
            return databaseProperties;
        }
        catch (IOException e){
            throw new IllegalStateException(e);
        }
    }

    public static String getToken() {
        final var properties = getAllProperties();
        return properties.getProperty("token");
    }
}