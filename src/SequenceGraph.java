/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SequenceGraph {
    Set<Vertex> vertices;
    List<StaticEdge> edges;

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public List<StaticEdge> getEdges() {
        return edges;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(List<StaticEdge> edges) {
        this.edges = edges;
    }

    public void addVertex (Vertex u){
        vertices.add(u);
    }

    public void addEdge (StaticEdge e){
        edges.add(e);
    }

    public void setVertices(List<Vertex> vertices) {
        HashSet<Vertex> tmp = new HashSet<>();
        for (Vertex u : vertices) {
            tmp.add(u);
        }
        this.vertices = tmp;

    }


    public SequenceGraph(){
        vertices = new HashSet<Vertex>();
        edges = new ArrayList<StaticEdge>();
    }
}
