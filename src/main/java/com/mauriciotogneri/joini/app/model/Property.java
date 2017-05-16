package com.mauriciotogneri.joini.app.model;

import com.mauriciotogneri.joini.app.app.Constants;

public class Property
{
    private final String key;
    private String value;

    public Property(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String key()
    {
        return key;
    }

    public String value()
    {
        return value;
    }

    public Boolean key(String key)
    {
        return this.key.equals(key);
    }

    public void join(Property property)
    {
        value = property.value;
    }

    @Override
    public String toString()
    {
        return String.format("%s%s%s = %s%n", Constants.TAB, Constants.TAB, key, value);
    }
}