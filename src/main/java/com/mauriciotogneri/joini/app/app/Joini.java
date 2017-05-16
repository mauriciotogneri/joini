package com.mauriciotogneri.joini.app.app;

import com.mauriciotogneri.joini.app.model.Catalogue;

import java.io.File;

public class Joini
{
    public static void main(String[] args)
    {
        if (args.length >= 1)
        {
            try
            {
                Parameters parameters = new Parameters(args[0]);

                Joini joini = new Joini();
                joini.join(parameters.source, parameters.target, parameters.options);
            }
            catch (Exception e)
            {
                e.printStackTrace();

                System.exit(-1);
            }
        }
        else
        {
            System.err.println("Usage: java -jar app.jar PATH_CONFIG_FILE");
        }
    }

    public void join(File source, File target, Options options) throws Exception
    {
        Catalogue sourceCatalogue = Catalogue.fromIni(source);
        Catalogue targetCatalogue = Catalogue.fromIni(target);
        targetCatalogue.join(sourceCatalogue, options);
    }
}