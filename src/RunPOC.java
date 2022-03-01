/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class RunPOC {

    public static void main (String[] args){

        TemporalGraph L = new TemporalGraph();
        SequenceGraph P = new SequenceGraph();

        try {
            L = ParserTools.parseTemporalGraph("rollernet.txt");
            P = ParserTools.parseSequenceGraph("sequencegraph.txt");
        }catch (IOException e){
            System.err.println("OH NO !");
        }

        int delta = 400;
        Date startTime = new Date();

        List<Isomorphism> result = Algorithm.computeAllIsomorphisms(L, P, delta);
        Date endTime = new Date();
        long timeElapsed = endTime.getTime() - startTime.getTime();

        System.out.println("Delta-isomorphisms computed in " + timeElapsed + " ms");
    }
}
