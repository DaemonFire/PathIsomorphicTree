/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StaticGraph {
    List<Vertex> vertices;
    List<StaticEdge> edges;

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<StaticEdge> getEdges() {
        return edges;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(List<StaticEdge> edges) {
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
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<StaticEdge>();
    }
}
