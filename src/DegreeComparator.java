/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.Comparator;

public class DegreeComparator implements Comparator<Vertex> {

    private StaticGraph G;

    public int degree (Vertex u, StaticGraph G){
        return (int) (G.getEdges().stream().filter( e -> e.getU()==u || e.getV()==u).distinct().count());
    }

    @Override
    public int compare(Vertex o1, Vertex o2) {
        if (degree(o1, G) > degree(o2, G)){
            return 1;
        }

        if (degree(o1, G) == degree(o2, G)){
            return 0;
        }

        if (degree(o1, G) < degree(o2,G)){
            return -1;
        }
        return 0;
    }

    public DegreeComparator (StaticGraph G){
        this.G = G;
    }
}
