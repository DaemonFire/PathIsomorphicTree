/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Isomorphism {

    private List<TemporalEdge> sequence;
    private int startInstant;
    private int order;
    private List<Vertex> a;
    private HashMap<Vertex, Vertex> mappings;
    private List<TemporalEdge> s;
    private Map<Integer, TemporalEdge> tmp;

    public Isomorphism(int startInstant){
        this.order = 0;
        this.startInstant = startInstant;
        this.sequence = new ArrayList<>();
        this.a = new ArrayList<>();
        this.mappings = new HashMap<>();
        this.s = new ArrayList<>();
        this.tmp = new HashMap<>();
    }

    public List<TemporalEdge> getSequence() {
        return sequence;
    }

    public int getStartInstant() {
        return startInstant;
    }

    public int getOrder() {
        return order;
    }

    public List<Vertex> getA() {
        return a;
    }

    public HashMap<Vertex, Vertex> getMappings() {
        return mappings;
    }

    public List<TemporalEdge> getS() {
        return s;
    }

    public void setSequence(List<TemporalEdge> sequence) {
        this.sequence = sequence;
    }

    public void setStartInstant(int startInstant) {
        this.startInstant = startInstant;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setA(List<Vertex> a) {
        this.a = a;
    }

    public void setMappings(HashMap<Vertex, Vertex> mappings) {
        this.mappings = mappings;
    }

    public void setS(List<TemporalEdge> s) {
        this.s = s;
    }

    public void addToS (TemporalEdge e){
        this.s.add(e);
    }

    public void addToMappings (Vertex u, Vertex v){
        this.mappings.put(u,v);
    }

    public void addToA(Vertex u){
        this.a.add(u);
    }

    public void addToSequence(TemporalEdge e){
        this.sequence.add(e);
    }

    public Map<Integer, TemporalEdge> getTmp() {
        return this.tmp;
    }

    public void setTmp(Map<Integer, TemporalEdge> tmp){
        this.tmp = tmp;
    }

    public void addToTmp(int i, TemporalEdge e) {
        tmp.put(i, e);
    }

    public void removeFromTmp(int i){
        tmp.remove(i);
    }
}
