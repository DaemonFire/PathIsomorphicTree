/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

public class TemporalEdge {

    Vertex u;
    Vertex v;
    int t;

    public Vertex getU() {
        return u;
    }

    public Vertex getV() {
        return v;
    }

    public int getT() {
        return t;
    }

    public void setU(Vertex u) {
        this.u = u;
    }

    public void setV(Vertex v) {
        this.v = v;
    }

    public void setT(int t) {
        this.t = t;
    }

    public TemporalEdge(Vertex u, Vertex v, int t) {
        this.u = u;
        this.v = v;
        this.t = t;
    }

    @Override
    public boolean equals(Object obj) {
        TemporalEdge e = (TemporalEdge)(obj);
        return ((this.getU() == e.getU() && this.getV() == e.getV() && this.getT() == e.getT()) || (this.getU() == e
                .getV() && this.getV() == e.getU() && this.getT() == e.getT()));
    }
}
