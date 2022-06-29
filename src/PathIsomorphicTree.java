/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PathIsomorphicTree {

    //CHANGE DIRECTORY PATH AND VALUE OF GAMMA HERE
    private static String directory = "data/rollernet";
    private static int delta = 71;

    public static void main (String[] args){

        File f = new File(directory);
        String filePath;

        String[] pathList = f.list();
        String[] dataSets = new String[pathList.length];

        for (int i = 0; i < pathList.length; i++) {
            dataSets[i] = directory + "/" + pathList[i];

        }

        ArrayList<String> output = new ArrayList<>();
        Path fichier = Paths.get("results.csv");

        for (String filepath : dataSets) {
            String line = new String();
            System.out.println("COMPUTING FOR " + filepath);
            line = line.concat(filepath + ",");

            TemporalGraph L = initiate(filepath);
            TemporalGraph P = initiate("path.txt");
           // if ((L.getEndInstant() - L.getStartInstant()) < 600000) {
                System.out.println("Number of vertices : " + L.getVertices().size() + ", Number of edges : " + L
                        .getEdges()

                        .size() + ", for " + (L.getEndInstant() - L.getStartInstant()) + " instants");
                line = line.concat(L.getVertices().size() + "," + L.getEdges().size() + "," + (L.getEndInstant() - L
                        .getStartInstant
                                ()) + ",,");
                line = line.concat(compute(L, P));
                output.add(line);
            //}

        }

    }

    static private TemporalGraph initiate(String filePath) {
        FileParser fp = null;
        try {
            fp = new FileParser(filePath);
        } catch (IOException e) {
            System.err.println("C'est cassé");
        }

        TemporalGraph ls = new TemporalGraph();
        ls.setStartInstant(fp.getLs().getStartInstant());
        ls.setEndInstant(fp.getLs().getEndInstant());
        fp.getLs().getVertices().stream().forEach(v->ls.getVertices().add(v));
        fp.getLs().getEdges().stream().forEach(e->ls.getEdges().add(e));

        return ls;

    }

    public static String compute(TemporalGraph ls, TemporalGraph p) {

        ArrayList<String> output = new ArrayList<>();
        String line = new String();
        ArrayList<TemporalIsomorphism> temporalIsomorphisms = new ArrayList<>();

        line = line.concat(computeTemporalIsomorphism(ls, p, line));

        output.add(line);

        System.out.println("Computation done ");
        return line;
    }

   public static String computeTemporalIsomorphism(TemporalGraph ls, TemporalGraph p, String line) {
        System.out.println("COMPUTING TEMPORAL ISOMORPHISM");
        ArrayList<TemporalIsomorphism> temporalIsomorphisms = new ArrayList<>();

        try {
            TemporalGraph P = new TemporalGraph();

            Date startTime = new Date();
            temporalIsomorphisms = Algorithm.computeTemporalIsomorphims(ls, p);
            Date endTime = new Date();
            long timeElapsed = endTime.getTime() - startTime.getTime();
            line = line.concat(timeElapsed + ",,");

        } catch (OutOfMemoryError e) {
            System.err.println("Out of memory");
            line = line.concat("OUT OF MEMORY,,");
        }
        System.out.println("We have " + temporalIsomorphisms.size() + " static isomorphisms");
        return ""+temporalIsomorphisms.size();
    }

}
