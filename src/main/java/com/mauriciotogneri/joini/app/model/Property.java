package com.mauriciotogneri.joini.app.model;

public class Property
{
    private final String key;
    private final String value;

    public Property(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String key()
    {
        return key;
    }

    public Boolean key(String key)
    {
        return this.key.equals(key);
    }

    @Override
    public String toString()
    {
        return String.format("\t\t%s = %s%n", key, value);
    }
}