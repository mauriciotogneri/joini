package com.mauriciotogneri.joini.app.app;

import java.io.File;
import java.io.IOException;

public class Parameters extends PropertiesFile
{
    public final File source;
    public final File target;
    public final Options options;

    public Parameters(String configPath) throws IOException
    {
        super(configPath);

        this.source = new File(stringProperty("source"));
        this.target = new File(stringProperty("target"));
        this.options = new Options(configPath);
    }
}