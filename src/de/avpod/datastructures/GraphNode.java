package de.avpod.datastructures;

import java.util.List;

public class GraphNode {
    public int value;
    public List<GraphNode> children;
    public boolean marked;
}
