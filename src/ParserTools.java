/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ParserTools {



    public static TemporalGraph parseTemporalGraph (String filePath, String label) throws IOException {
        HashMap<Integer,Vertex> indexToVertex;
        TemporalGraph ls;
        ArrayList<Vertex> vertices = new ArrayList<>();
        int startInstant = Integer.MAX_VALUE;
        int endInstant = 0;
        ls = new TemporalGraph();
        ls.setVertices(vertices);
        indexToVertex = new HashMap<>();
        BufferedReader r = new BufferedReader(new FileReader(filePath));
        String s = r.readLine();
        int i = 0;
        while (s != null) {
            String[] points = s.replaceAll("\n", "").split(",");
            Vertex v;
            Vertex u;
            if (indexToVertex.containsKey(Integer.parseInt(points[0]))){
                u = indexToVertex.get(Integer.parseInt(points[0]));
            }
            else {
                u = new Vertex(label+i);
                i++;
                indexToVertex.put(Integer.parseInt(points[0]), u);
                ls.getVertices().add(u);
            }
            if (indexToVertex.containsKey(Integer.parseInt(points[1]))){
                v = indexToVertex.get(Integer.parseInt(points[1]));
            }
            else {
                v = new Vertex(label+i);
                i++;
                indexToVertex.put(Integer.parseInt(points[1]), v);

                ls.getVertices().add(v);
            }
            ls.getEdges().add(new TemporalEdge(u, v, Integer.parseInt(points[2])));
            if (Integer.parseInt(points[2])>endInstant) {
                endInstant = Integer.parseInt(points[2]);
            }
            if (Integer.parseInt(points[2])<startInstant) {
                startInstant = Integer.parseInt(points[2]);
            }

            s = r.readLine();
        }

        ls.setStartInstant(startInstant);
        ls.setEndInstant(endInstant);
        return ls;
    }

}
