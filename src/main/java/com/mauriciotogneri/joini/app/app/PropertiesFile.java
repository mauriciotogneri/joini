package com.mauriciotogneri.joini.app.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile
{
    private final Properties properties;

    public PropertiesFile(String configPath) throws IOException
    {
        this.properties = new Properties();
        this.properties.load(new FileInputStream(configPath));
    }

    protected String stringProperty(String property)
    {
        return properties.getProperty(property);
    }

    protected boolean booleanProperty(String property)
    {
        String value = properties.getProperty(property);

        return (value != null) && (value.equals("true") || value.equals("y"));
    }
}