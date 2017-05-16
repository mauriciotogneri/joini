package com.mauriciotogneri.joini.app.model;

import com.mauriciotogneri.joini.app.app.Options;

import java.util.ArrayList;
import java.util.List;

public class Item
{
    private final String name;
    private final List<Property> properties = new ArrayList<>();

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

    public void join(Group group, Item item, Options options)
    {
        for (Property property : item.properties)
        {
            Property localProperty = property(property.key());

            if (localProperty != null)
            {
                add(property);
            }
            else if (options.createProperties)
            {
                add(new Property(property.key(), property.value()));
            }
            else
            {
                throw new RuntimeException(String.format("Property not found in target: %s.%s.%s", group, item, property));
            }
        }
    }

    private Property property(String key)
    {
        for (Property property : properties)
        {
            if (property.key(key))
            {
                return property;
            }
        }

        return null;
    }

    public void add(Property property)
    {
        properties.add(property);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("\t[%s]%n", name));

        for (Property property : properties)
        {
            builder.append(property.toString());
        }

        builder.append(String.format("%n"));

        return builder.toString();
    }
}