package com.mauriciotogneri.joini.app.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mauriciotogneri.joini.app.app.Options;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Catalogue
{
    private final List<Group> groups = new ArrayList<>();

    public void add(Group group)
    {
        groups.add(group);
    }

    public void join(Catalogue catalogue, Options options)
    {
        for (Group group : catalogue.groups)
        {
            Group localGroup = group(group.name());

            if (localGroup != null)
            {
                localGroup.join(group, options);
            }
            else if (options.createGroups)
            {
                add(new Group(group.name()));
            }
            else
            {
                throw new RuntimeException(String.format("Group not found in target: %s", group));
            }
        }
    }

    private Group group(String name)
    {
        for (Group group : groups)
        {
            if (group.name(name))
            {
                return group;
            }
        }

        return null;
    }

    public static Catalogue fromIni(File file) throws Exception
    {
        return fromIni(new Ini(file));
    }

    public static Catalogue fromIni(InputStream inputStream) throws Exception
    {
        return fromIni(new Ini(inputStream));
    }

    public static Catalogue fromIni(String string) throws Exception
    {
        return fromIni(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)));
    }

    public static Catalogue fromIni(Reader reader) throws Exception
    {
        return fromIni(new Ini(reader));
    }

    private static Catalogue fromIni(Ini ini) throws Exception
    {
        Catalogue catalogue = new Catalogue();

        Group currentGroup = null;

        for (String name : ini.keySet())
        {
            Section section = ini.get(name);

            if (section.isEmpty())
            {
                if (currentGroup != null)
                {
                    catalogue.add(currentGroup);
                }

                currentGroup = new Group(name.replace("[", "").replace("]", ""));
            }
            else
            {
                Item item = new Item(name);

                for (String key : section.keySet())
                {
                    Property property = new Property(key, section.get(key));
                    item.add(property);
                }

                if (currentGroup != null)
                {
                    currentGroup.add(item);
                }
            }
        }

        if (currentGroup != null)
        {
            catalogue.add(currentGroup);
        }

        return catalogue;
    }

    public static Catalogue fromJson(String json)
    {
        return fromJson(new JsonParser().parse(json).getAsJsonObject());
    }

    public static Catalogue fromJson(File file) throws Exception
    {
        return fromJson(new FileInputStream(file));
    }

    public static Catalogue fromJson(InputStream inputStream)
    {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

        return fromJson(buffer.lines().collect(Collectors.joining("\n")));
    }

    public static Catalogue fromJson(Reader reader)
    {
        return fromJson(new JsonParser().parse(reader).getAsJsonObject());
    }

    private static Catalogue fromJson(JsonObject json)
    {
        Catalogue catalogue = new Catalogue();

        for (Entry<String, JsonElement> groupEntry : json.entrySet())
        {
            JsonObject jsonGroup = groupEntry.getValue().getAsJsonObject();

            Group group = new Group(groupEntry.getKey());

            for (Entry<String, JsonElement> itemEntry : jsonGroup.entrySet())
            {
                JsonObject jsonItem = itemEntry.getValue().getAsJsonObject();

                Item item = new Item(itemEntry.getKey());

                for (Entry<String, JsonElement> propertyEntry : jsonItem.entrySet())
                {
                    Property property = new Property(propertyEntry.getKey(), propertyEntry.getValue().getAsString());
                    item.add(property);
                }

                group.add(item);
            }

            catalogue.add(group);
        }

        return catalogue;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (Group group : groups)
        {
            builder.append(group.toString());
        }

        return builder.toString();
    }
}