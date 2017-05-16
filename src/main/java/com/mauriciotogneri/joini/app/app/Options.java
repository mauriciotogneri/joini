package com.mauriciotogneri.joini.app.app;

import java.io.IOException;

public class Options extends PropertiesFile
{
    public final Boolean createGroups;
    public final Boolean createItems;
    public final Boolean createProperties;

    public Options(String configPath) throws IOException
    {
        super(configPath);

        this.createGroups = booleanProperty("create_groups");
        this.createItems = booleanProperty("create_items");
        this.createProperties = booleanProperty("create_properties");
    }
}