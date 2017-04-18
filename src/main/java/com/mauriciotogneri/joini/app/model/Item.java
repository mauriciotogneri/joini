package com.mauriciotogneri.joini.app.model;

import com.mauriciotogneri.joini.app.app.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Item
{
    private final String name;
    private final Map<String, String> properties = new HashMap<>();

    public Item(String name)
    {
        this.name = name;
    }

    public String name()
    {
        return name;
    }

    public Boolean name(String name)
    {
        return this.name.equals(name);
    }

    public void join(Group group, Item item)
    {
        for (String key : item.properties.keySet())
        {
            String localValue = property(key);

            if (localValue != null)
            {
                properties.put(key, localValue);
            }
            else
            {
                System.err.println(String.format("Translation not found in INI: %s.%s.%s", group, item, key));
            }
        }
    }

    private String property(String key)
    {
        for (Entry<String, String> entry : properties.entrySet())
        {
            if (entry.getKey().equals(key))
            {
                return entry.getValue();
            }
        }

        return null;
    }

    public void add(String key, String value)
    {
        properties.put(key, value);
    }

    /*public String getTranslationContent(String locale)
    {
        for (Translation translation : translations)
        {
            if (translation.locale().equals(locale))
            {
                return translation.content();
            }
        }

        return "";
    }

    public boolean isTranslatedFor(String locale)
    {
        for (Translation translation : translations)
        {
            if (translation.locale().equals(locale))
            {
                return translation.translated();
            }
        }

        return false;
    }

    public boolean isTranslatedForAll(List<String> locales)
    {
        int translationsFound = 0;

        for (Translation translation : translations)
        {
            if (locales.contains(translation.locale()) && translation.translated())
            {
                translationsFound++;
            }
        }

        return translationsFound == locales.size();
    }*/

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s[%s]%n", Constants.TAB, name));

        for (Entry<String, String> entry : properties.entrySet())
        {
            builder.append(String.format("%s%s%s = %s%n", Constants.TAB, Constants.TAB, entry.getKey(), entry.getValue()));
        }

        builder.append(String.format("%n"));

        return builder.toString();
    }
}