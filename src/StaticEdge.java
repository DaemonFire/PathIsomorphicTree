/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

public class StaticEdge {

    private Vertex u;
    private Vertex v;

    public Vertex getU() {
        return u;
    }

    public Vertex getV() {
        return v;
    }

    public void setU(Vertex u) {
        this.u = u;
    }

    public void setV(Vertex v) {
        this.v = v;
    }

    public StaticEdge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object obj) {
        StaticEdge e = (StaticEdge) (obj);
        return ((this.getU() == e.getU() && this.getV() == e.getV()) || (this.getU() == e.getV() && this.getV() == e
                .getU()));
    }
}
