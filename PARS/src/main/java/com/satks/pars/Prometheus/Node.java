
package com.satks.pars.Prometheus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Node 
{
    private final String name;
    private final Map<String, Node> children;

    public Node(String name)
    {
        this.name = name;
        this.children = new LinkedHashMap<String, Node>();
    }

    public String getName()
    {
        return name;
    }

    public void addChild(Node child) 
    {
        children.put(child.getName(), child);
    }

    public void removeChild(String name)
    {
        children.remove(name);
    }

    public Node getChild(String name)
    {
        return children.get(name);
    }
 
    public Set<Node> getChildren()
    {
        return Collections.unmodifiableSet(
            new LinkedHashSet<Node>(children.values()));
    }
}