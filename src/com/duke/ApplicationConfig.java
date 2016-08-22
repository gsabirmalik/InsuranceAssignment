package com.duke;

import java.io.FileReader;
import java.util.Properties;

public class ApplicationConfig {

    public static String AuthenticationToken() {

        return GetValueByKey("authenticationToken");
    }

    private static String GetValueByKey(String key) {

        try (FileReader reader = new FileReader("ApplicationConfig.properties")) {

            Properties properties = new Properties();
            properties.load(reader);

            return properties.getProperty(key);
        } catch (Exception e) {
            return "";
        }
    }
}