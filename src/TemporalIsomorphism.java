/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.HashMap;
import java.util.List;

public class TemporalIsomorphism {

    private HashMap<Integer, List<TemporalEdge>> toMatch;
    private int startInstant;
    private int endInstant;
    private HashMap<Vertex, Vertex> mappings;
    private int order;

    public TemporalIsomorphism(int startInstant){
        this.startInstant = startInstant;
        this.toMatch = new HashMap<>();
        this.mappings = new HashMap<>();
        this.endInstant = -1;
        this.order = 0;
    }

    public HashMap<Integer,List<TemporalEdge>> getToMatch() {
        return toMatch;
    }

    public int getOrder(){
        return order;
    }

    public int getStartInstant() {
        return startInstant;
    }

    public HashMap<Vertex, Vertex> getMappings() {
        return mappings;
    }

    public void setOrder(int order){
        this.order = order;
    }

    public void setToMatch(HashMap<Integer, List<TemporalEdge>> toMatch) {
        this.toMatch = toMatch;
    }

    public void setStartInstant(int startInstant) {
        this.startInstant = startInstant;
    }

    public void setMappings(HashMap<Vertex, Vertex> mappings) {
        this.mappings = mappings;
    }

    public void removeToMatch(int i, TemporalEdge e){
        for (TemporalEdge f : toMatch.get(i)){
            if (f.equals(e)){
                toMatch.get(i).remove(f);
                break;
            }
        }
    }

    public int getEndInstant() {
        return endInstant;
    }

    public void setEndInstant(int endInstant) {
        this.endInstant = endInstant;
    }
}
