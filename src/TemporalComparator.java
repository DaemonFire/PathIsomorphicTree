/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.Comparator;

public class TemporalComparator implements Comparator<TemporalEdge> {



    @Override
    public int compare(TemporalEdge o1, TemporalEdge o2) {
        if (o1.getT() > o2.getT()){
            return 1;
        }

        if (o1.getT() == o2.getT()){
            return 0;
        }

        if (o1.getT() < o2.getT()){
            return -1;
        }
        return 0;
    }

    public TemporalComparator(){

    }
}
