package com.mauriciotogneri.joini.app.model;

import java.util.ArrayList;
import java.util.List;

public class Group
{
    private final String name;
    private final List<Item> items = new ArrayList<>();

    public Group(String name)
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

    public void add(Item item)
    {
        items.add(item);
    }

    public void join(Group group)
    {
        for (Item item : group.items)
        {
            Item localItem = item(item.name());

            if (localItem != null)
            {
                localItem.join(this, item);
            }
            else
            {
                System.err.println(String.format("Item not found in INI: %s.%s", name, item));
            }
        }
    }

    private Item item(String name)
    {
        for (Item item : items)
        {
            if (item.name(name))
            {
                return item;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("[[%s]]%n%n", name));

        for (Item item : items)
        {
            builder.append(item.toString());
        }

        builder.append(String.format("%n"));

        return builder.toString();
    }
}