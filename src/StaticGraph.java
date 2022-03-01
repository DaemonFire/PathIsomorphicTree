/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.HashSet;
import java.util.Set;

public class StaticGraph {
    Set<Vertex> vertices;
    Set<StaticEdge> edges;

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<StaticEdge> getEdges() {
        return edges;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(Set<StaticEdge> edges) {
        this.edges = edges;
    }

    public void addVertex(Vertex u){
        this.vertices.add(u);
    }

    public void addEdge(StaticEdge e){
        this.edges.add(e);
    }

    public void removeEdge(StaticEdge e){
        this.edges.remove(e);
    }

    public StaticGraph(){
        this.vertices = new HashSet<Vertex>();
        this.edges = new HashSet<StaticEdge>();
    }
}
