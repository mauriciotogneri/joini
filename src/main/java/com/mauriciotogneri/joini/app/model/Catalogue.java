package com.mauriciotogneri.joini.app.model;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Catalogue
{
    private final List<Group> groups = new ArrayList<>();

    public void add(Group group)
    {
        groups.add(group);
    }

    public void join(Catalogue catalogue)
    {
        for (Group group : catalogue.groups)
        {
            Group localGroup = group(group.name());

            if (localGroup != null)
            {
                localGroup.join(group);
            }
            else
            {
                System.err.println(String.format("Group not found in INI: %s", group));
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
        Catalogue catalogue = new Catalogue();

        Ini ini = new Ini(file);

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