/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TemporalGraph {

    private List<Vertex> vertices;
    private List<TemporalEdge> edges;
    private int startInstant;
    private int endInstant;

    public List<Vertex> getVertices(){
        return vertices;
    }

    public List<TemporalEdge> getEdges() {
        return edges;
    }

    public int getStartInstant() {
        return startInstant;
    }

    public int getEndInstant() {
        return endInstant;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(List<TemporalEdge> edges) {
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
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<TemporalEdge>();
        this.startInstant = 0;
        this.endInstant = 0;
    }
}
