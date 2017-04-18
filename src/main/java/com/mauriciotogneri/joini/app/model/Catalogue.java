package com.mauriciotogneri.joini.app.model;

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