/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.HashMap;

public class StaticIsomorphism {

    private HashMap<Vertex, Vertex> mapping;

    public StaticIsomorphism (){
        this.mapping = new HashMap<>();
    }

    public HashMap<Vertex, Vertex> getMapping(){
        return this.mapping;
    }

    public void setMapping(HashMap<Vertex, Vertex> map){
        this.mapping = map;
    }

    public void addMapping(Vertex u, Vertex v){
        this.mapping.put(u,v);
    }
}
