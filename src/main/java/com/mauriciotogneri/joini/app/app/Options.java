package com.mauriciotogneri.joini.app.app;

import java.io.IOException;

public class Options extends PropertiesFile
{
    public final Boolean createCatalogues;

    public Options(String configPath) throws IOException
    {
        super(configPath);

        this.createCatalogues = booleanProperty("create_categories");
    }
}