/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TemporalGraph {

    private Set<Vertex> vertices;
    private Set<TemporalEdge> edges;
    private int startInstant;
    private int endInstant;

    public Set<Vertex> getVertices(){
        return vertices;
    }

    public Set<TemporalEdge> getEdges() {
        return edges;
    }

    public int getStartInstant() {
        return startInstant;
    }

    public int getEndInstant() {
        return endInstant;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        HashSet<Vertex> tmp = new HashSet<>();
        for (Vertex u : vertices) {
            tmp.add(u);
        }
        this.vertices = tmp;

    }


    public void setEdges(Set<TemporalEdge> edges) {
        this.edges = edges;
    }

    public void setStartInstant(int startInstant) {
        this.startInstant = startInstant;
    }

    public void setEndInstant(int endInstant) {
        this.endInstant = endInstant;
    }

    public void addVertex(Vertex u){
        vertices.add(u);
    }

    public void addTemporalEdge(TemporalEdge e){
        edges.add(e);
    }

    public TemporalGraph (){
        this.vertices = new HashSet<Vertex>();
        this.edges = new HashSet<TemporalEdge>();
        this.startInstant = 0;
        this.endInstant = 0;
    }
}
