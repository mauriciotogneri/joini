package com.mauriciotogneri.joini.app.app;

import com.mauriciotogneri.joini.app.model.Catalogue;

import java.io.File;

public class Joini
{
    public static void main(String[] args) throws Exception
    {
        File ini = new File("/home/mauricio/test.ini");
        Catalogue catalogue = Catalogue.fromIni(ini);
        System.out.println(catalogue);
    }
}