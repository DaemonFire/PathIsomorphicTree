/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Algorithm {

    public static StaticGraph staticThisGraph(TemporalGraph L) {
        StaticGraph Lstat = new StaticGraph();
        Lstat.setVertices(L.getVertices());
        boolean[][] matrix = new boolean[L.getVertices().size()][L.getVertices().size()];

        for (TemporalEdge e : L.getEdges()) {
            if (matrix[L.getVertices().indexOf(e.getU())][L.getVertices().indexOf(e.getV())] != true) {
                StaticEdge estat = new StaticEdge(e.getU(), e.getV());
                Lstat.addEdge(estat);
                matrix[L.getVertices().indexOf(e.getU())][L.getVertices().indexOf(e.getV())] = true;
            }
        }
        return Lstat;
    }

    public static StaticGraph staticThisGraph(SequenceGraph P) {
        StaticGraph Pstat = new StaticGraph();
        Pstat.setVertices(P.getVertices());
        boolean[][] matrix = new boolean[P.getVertices().size()][P.getVertices().size()];

        for (StaticEdge e : P.getEdges()) {
            if (matrix[P.getVertices().indexOf(e.getU())][P.getVertices().indexOf(e.getV())] != true) {
                Pstat.addEdge(e);
                matrix[P.getVertices().indexOf(e.getU())][P.getVertices().indexOf(e.getV())] = true;
            }
        }
        return Pstat;
    }

    public static StaticGraph invertThisGraph(StaticGraph G) {
        StaticGraph Gbar = new StaticGraph();
        Gbar.setVertices(G.vertices);

        for (Vertex x : Gbar.getVertices()) {
            for (Vertex y : Gbar.getVertices()) {
                if (x != y) {
                    StaticEdge e = new StaticEdge(x, y);

                    if (!G.getEdges().contains(e)) {
                        Gbar.addEdge(e);
                    }
                }
            }
        }

        return Gbar;
    }

    public static List<List<Vertex>> findCliques(StaticGraph G, int n, int depth) {
        DegreeComparator degreeComparator = new DegreeComparator(G);
        List<Vertex> orderedvertices = G.getVertices().stream().sorted(degreeComparator).filter(v -> degreeComparator
                .degree(v, G) >= n - depth).collect(Collectors.toList());
        List<Vertex> vertices = new ArrayList<>();
        for (int j = orderedvertices.size() - 1; j >= 0; j--) {
            vertices.add(orderedvertices.get(j));
        }
        int i = 0;
        List<List<Vertex>> result = new ArrayList<>();

        if (depth == n) {
            for (Vertex u : vertices) {
                List<Vertex> clique = new ArrayList<>();
                clique.add(u);
                result.add(clique);
            }
        } else {
            while (depth + vertices.size() - (i + 1) >= n) {
                List<Vertex> neighbourhood = new ArrayList<>();
                final int j = i;
                G.getEdges().stream().filter(e -> (e.getU() == vertices.get(j) || e.getV() == vertices.get(j)))
                        .forEach(e

                                -> {
                            if (e.getU().equals(vertices.get(j)) && !neighbourhood.contains(e.getV())) {
                                neighbourhood.add(e.getV());
                            } else if (e.getV().equals(vertices.get(j)) && !neighbourhood.contains(e.getU())) {
                                neighbourhood.add(e.getU());
                            }
                        });
                StaticGraph Gdeep = new StaticGraph();
                for (Vertex u : neighbourhood) {
                    Gdeep.addVertex(u);
                }
                for (StaticEdge e : G.getEdges()) {
                    if ((Gdeep.getVertices().contains(e.getU()) && Gdeep.getVertices().contains(e.getV()))) {
                        Gdeep.addEdge(e);
                    }

                }

                List<List<Vertex>> deeperCliques = findCliques(Gdeep, n, depth + 1);

                if (deeperCliques.size() != 0) {
                    for (List<Vertex> l : deeperCliques) {
                        l.add(vertices.get(i));
                        boolean alreadyin = false;
                        for (List<Vertex> r : result) {
                            List<Vertex> tmp = new ArrayList<>(r);
                            tmp.removeAll(l);
                            if (tmp.isEmpty()) {
                                alreadyin = true;
                                break;
                            }
                        }
                        if (!alreadyin) {
                            result.add(l);
                        }
                    }
                }
                i++;
            }
        }

        return result;
    }

    public static List<StaticIsomorphism> computeAllIsomorphims(StaticGraph G, StaticGraph P){
        HashMap<Vertex, StaticEdge> vertexToMapping = new HashMap<>();

        StaticGraph M = new StaticGraph();

        DegreeComparator GComp = new DegreeComparator(G);
        DegreeComparator PComp = new DegreeComparator(P);

        for (Vertex x : G.getVertices()) {
            for (Vertex y : P.getVertices()) {
                if (GComp.degree(x, G) >= PComp.degree(y, P)) {
                    Vertex u = new Vertex(x.getLabel() + "->" + y.getLabel());
                    StaticEdge e = new StaticEdge(x, y);
                    vertexToMapping.put(u, e);
                    M.addVertex(u);
                }
            }
        }

        StaticGraph PBar = invertThisGraph(P);
        StaticGraph GBar = invertThisGraph(G);
        Date startTime = new Date();

        for (StaticEdge e1 : P.getEdges()) {
            for (StaticEdge e2 : G.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());

                if (vertexToMapping.containsValue(m11) && vertexToMapping.containsValue(m12)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m11)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m12)).findFirst()
                            .get().getKey();
                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
                if (vertexToMapping.containsValue(m21) && vertexToMapping.containsValue(m22)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m21)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m22)).findFirst()
                            .get().getKey();

                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
            }
        }

        for (StaticEdge e1 : PBar.getEdges()) {
            for (StaticEdge e2 : GBar.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());

                if (vertexToMapping.containsValue(m11) && vertexToMapping.containsValue(m12)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m11)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m12)).findFirst()
                            .get().getKey();
                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
                if (vertexToMapping.containsValue(m21) && vertexToMapping.containsValue(m22)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m21)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m22)).findFirst()
                            .get().getKey();

                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
            }
        }

        for (StaticEdge e1 : PBar.getEdges()) {
            for (StaticEdge e2 : G.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());
                Vertex a1 = null;
                Vertex b1 = null;
                Vertex a2 = null;
                Vertex b2 = null;

                Optional<Entry<Vertex, StaticEdge>> oa1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m11)).findFirst();
                if (oa1.isPresent()) {
                    a1 = oa1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m12)).findFirst();
                if (ob1.isPresent()) {
                    b1 = ob1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> oa2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m21)).findFirst();
                if (oa2.isPresent()) {
                    a2 = oa2.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m22)).findFirst();
                if (ob2.isPresent()) {
                    b2 = ob2.get().getKey();
                }

                StaticEdge ab1 = new StaticEdge(a1, b1);
                StaticEdge ab2 = new StaticEdge(a2, b2);

                if (M.getEdges().contains(ab1)) {
                    M.removeEdge(ab1);
                }
                if (M.getEdges().contains(ab2)) {
                    M.removeEdge(ab2);
                }
            }
        }

        for (StaticEdge e1 : P.getEdges()) {
            for (StaticEdge e2 : GBar.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());
                Vertex a1 = null;
                Vertex b1 = null;
                Vertex a2 = null;
                Vertex b2 = null;

                Optional<Entry<Vertex, StaticEdge>> oa1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m11)).findFirst();
                if (oa1.isPresent()) {
                    a1 = oa1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m12)).findFirst();
                if (ob1.isPresent()) {
                    b1 = ob1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> oa2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m21)).findFirst();
                if (oa2.isPresent()) {
                    a2 = oa2.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m22)).findFirst();
                if (ob2.isPresent()) {
                    b2 = ob2.get().getKey();
                }

                StaticEdge ab1 = new StaticEdge(a1, b1);
                StaticEdge ab2 = new StaticEdge(a2, b2);

                if (M.getEdges().contains(ab1)) {
                    M.removeEdge(ab1);
                }
                if (M.getEdges().contains(ab2)) {
                    M.removeEdge(ab2);
                }
            }
        }

        ArrayList<StaticEdge> toDelete = new ArrayList<>();

        for (StaticEdge e : M.getEdges()) {
            StaticEdge m1 = vertexToMapping.get(e.getU());
            StaticEdge m2 = vertexToMapping.get(e.getV());

            Vertex xm1 = m1.getU();
            Vertex ym1 = m1.getV();
            Vertex xm2 = m2.getU();
            Vertex ym2 = m2.getV();

            if ((xm1 == xm2) || (ym1 == ym2)) {
                toDelete.add(e);
            }
        }

        for (StaticEdge e : toDelete) {
            M.removeEdge(e);
        }

        Date endTime = new Date();

        long timeElapsed = endTime.getTime() - startTime.getTime();

        System.out.println("M constructed in " + timeElapsed + " ms");
        startTime = new Date();
        List<List<Vertex>> cliques = findCliques(M, P.getVertices().size(), 1);

        endTime = new Date();
        timeElapsed = endTime.getTime() - startTime.getTime();

        System.out.println("Cliques computed in " + timeElapsed + " ms");
        //   System.out.println(cliques.stream().filter(c -> isClique(c, M)).count()+"/"+cliques.stream().count());
        int count = 1;
        ArrayList<StaticIsomorphism> result = new ArrayList<>();
        for (List<Vertex> c : cliques){
            StaticIsomorphism i = new StaticIsomorphism();
            System.out.println("Isomorphism " + count + ":");
            for (Vertex v : c){
                i.addMapping(vertexToMapping.get(v).getU(), vertexToMapping.get(v).getV());
                System.out.println(vertexToMapping.get(v).getU().getLabel()+" => "+ vertexToMapping.get(v).getV().getLabel());
            }
            result.add(i);
            count ++;
        }

        return result;
    }

    public static List<Isomorphism> computeAllSequentialIsomorphisms(TemporalGraph L, SequenceGraph P, int delta) {
        StaticGraph Lstat = staticThisGraph(L);
        StaticGraph Pstat = staticThisGraph(P);

        HashMap<Vertex, StaticEdge> vertexToMapping = new HashMap<>();

        StaticGraph M = new StaticGraph();

        DegreeComparator LstatComp = new DegreeComparator(Lstat);
        DegreeComparator PstatComp = new DegreeComparator(Pstat);

        for (Vertex x : Lstat.getVertices()) {
            for (Vertex y : Pstat.getVertices()) {
                if (LstatComp.degree(x, Lstat) >= PstatComp.degree(y, Pstat)) {
                    Vertex u = new Vertex(x.getLabel() + "->" + y.getLabel());
                    StaticEdge e = new StaticEdge(x, y);
                    vertexToMapping.put(u, e);
                    M.addVertex(u);
                }
            }
        }

        StaticGraph PstatBar = invertThisGraph(Pstat);
        StaticGraph LstatBar = invertThisGraph(Lstat);
        Date startTime = new Date();

        for (StaticEdge e1 : Pstat.getEdges()) {
            for (StaticEdge e2 : Lstat.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());

                if (vertexToMapping.containsValue(m11) && vertexToMapping.containsValue(m12)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m11)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m12)).findFirst()
                            .get().getKey();
                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
                if (vertexToMapping.containsValue(m21) && vertexToMapping.containsValue(m22)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m21)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m22)).findFirst()
                            .get().getKey();

                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
            }
        }

        for (StaticEdge e1 : PstatBar.getEdges()) {
            for (StaticEdge e2 : LstatBar.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());

                if (vertexToMapping.containsValue(m11) && vertexToMapping.containsValue(m12)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m11)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m12)).findFirst()
                            .get().getKey();
                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
                if (vertexToMapping.containsValue(m21) && vertexToMapping.containsValue(m22)) {
                    Vertex a = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m21)).findFirst()
                            .get().getKey();
                    Vertex b = vertexToMapping.entrySet().stream().filter(e -> e.getValue().equals(m22)).findFirst()
                            .get().getKey();

                    StaticEdge ab = new StaticEdge(a, b);
                    if (!M.getEdges().contains(ab)) {
                        M.addEdge(ab);
                    }
                }
            }
        }

        for (StaticEdge e1 : Pstat.getEdges()) {
            for (StaticEdge e2 : LstatBar.getEdges()) {
                StaticEdge m11 = new StaticEdge(e1.getU(), e2.getU());
                StaticEdge m12 = new StaticEdge(e1.getV(), e2.getV());
                StaticEdge m21 = new StaticEdge(e1.getU(), e2.getV());
                StaticEdge m22 = new StaticEdge(e1.getV(), e2.getU());
                Vertex a1 = null;
                Vertex b1 = null;
                Vertex a2 = null;
                Vertex b2 = null;

                Optional<Entry<Vertex, StaticEdge>> oa1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m11)).findFirst();
                if (oa1.isPresent()) {
                    a1 = oa1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob1 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m12)).findFirst();
                if (ob1.isPresent()) {
                    b1 = ob1.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> oa2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m21)).findFirst();
                if (oa2.isPresent()) {
                    a2 = oa2.get().getKey();
                }
                Optional<Entry<Vertex, StaticEdge>> ob2 = vertexToMapping.entrySet().stream().filter(e -> e
                        .getValue().equals(m22)).findFirst();
                if (ob2.isPresent()) {
                    b2 = ob2.get().getKey();
                }

                StaticEdge ab1 = new StaticEdge(a1, b1);
                StaticEdge ab2 = new StaticEdge(a2, b2);

                if (M.getEdges().contains(ab1)) {
                    M.removeEdge(ab1);
                }
                if (M.getEdges().contains(ab2)) {
                    M.removeEdge(ab2);
                }
            }
        }

        ArrayList<StaticEdge> toDelete = new ArrayList<>();

        for (StaticEdge e : M.getEdges()) {
            StaticEdge m1 = vertexToMapping.get(e.getU());
            StaticEdge m2 = vertexToMapping.get(e.getV());

            Vertex xm1 = m1.getU();
            Vertex ym1 = m1.getV();
            Vertex xm2 = m2.getU();
            Vertex ym2 = m2.getV();

            if ((xm1 == xm2) || (ym1 == ym2)) {
                toDelete.add(e);
            }
        }

        for (StaticEdge e : toDelete) {
            M.removeEdge(e);
        }

        Date endTime = new Date();

        long timeElapsed = endTime.getTime() - startTime.getTime();

        System.out.println("M constructed in " + timeElapsed + " ms");
        startTime = new Date();
        List<List<Vertex>> cliques = findCliques(M, P.getVertices().size(), 1);

        endTime = new Date();
        timeElapsed = endTime.getTime() - startTime.getTime();

        System.out.println("Cliques computed in " + timeElapsed + " ms");
        //   System.out.println(cliques.stream().filter(c -> isClique(c, M)).count()+"/"+cliques.stream().count());

        TemporalComparator temporalComparator = new TemporalComparator();

        List<Isomorphism> isomorphisms = new ArrayList<>();
        int count = 1;
        for (List<Vertex> c : cliques) {
            Isomorphism isomorphism = new Isomorphism(0);
            for (Vertex u : c) {
                isomorphism.addToA(vertexToMapping.get(u).getU());
                isomorphism.addToMappings(vertexToMapping.get(u).getU(), vertexToMapping.get(u).getV());
            }

            for (TemporalEdge e : L.getEdges()) {
                if (isomorphism.getA().contains(e.getU()) && isomorphism.getA().contains(e.getV())) {
                    isomorphism.addToS(e);
                    isomorphism.getS().sort(temporalComparator);
                }
            }

            List<Isomorphism> tempIso = new ArrayList<>();
            tempIso.add(isomorphism);
            for (TemporalEdge e : isomorphism.getS()) {
                //  System.out.println("Exploring edge n°"+isomorphism.getS().indexOf(e)+"/"+isomorphism.getS().size
                // ()+" with "+tempIso.size() + " prospective isomorphisms");
                int i = 0;
                while (i < P.getEdges().size()) {
                    if ((P.getEdges().get(i).getU().equals(isomorphism.getMappings().get(e.getU())) && P.getEdges()
                            .get(i).getV().equals(isomorphism.getMappings().get(e.getV()))) || (P.getEdges().get(i)
                            .getU().equals(isomorphism.getMappings().get(e.getV())) && P.getEdges().get(i).getV()
                            .equals(isomorphism.getMappings().get(e.getU())))) {
                        break;
                    }
                    i++;
                }
       /*         if (i == 0) {
                    Isomorphism iso = new Isomorphism(e.getT());
                    iso.setS(isomorphism.getS());
                    iso.setA(isomorphism.getA());
                    iso.setMappings(isomorphism.getMappings());
                    iso.setStartInstant(e.getT());
                    iso.setOrder(1);
                    iso.addToSequence(e);
                    iso.setTmp(isomorphism.getTmp());
                    if (isomorphism.getOrder() == 0) {
                        isomorphism = iso;
                    } else {
                        tempIso.add(iso);
                    }
                } else {
*/
                ArrayList<Isomorphism> toAdd = new ArrayList<>();
                ArrayList<Isomorphism> toRemove = new ArrayList<>();
                for (Isomorphism iso : tempIso) {
                    if ((iso.getOrder() != P.getEdges().size()) && (e.getT() - iso.getStartInstant() > delta)) {
                        toRemove.add(iso);
                    } else {
                        if (iso.getOrder() == i) {
                            if (iso.getOrder() == 0) {
                                iso.setStartInstant(e.getT());
                            }
                            iso.addToSequence(e);
                            iso.setOrder(i + 1);
                        } else if (iso.getOrder() > i) {
                            Isomorphism isomorphism2 = new Isomorphism(iso.getStartInstant());
                            isomorphism2.setOrder(i + 1);
                            for (int j = 0; j < i; j++) {
                                isomorphism2.addToSequence(iso.getSequence().get(j));
                            }
                            for (int j = 0; j < P.getEdges().size(); j++) {
                                if (iso.getTmp().containsKey(j)) {
                                    isomorphism2.addToTmp(j, iso.getTmp().get(j));
                                }
                            }
                            isomorphism2.setS(iso.getS());
                            isomorphism2.setA(iso.getA());
                            isomorphism2.setMappings(iso.getMappings());
                            isomorphism2.addToSequence(e);
                            toAdd.add(isomorphism2);
                        } else {
                            if (!iso.getTmp().containsKey(i)) {
                                iso.addToTmp(i, e);
                            } else {
                                Isomorphism isomorphism2 = new Isomorphism(iso.getStartInstant());
                                isomorphism2.setOrder(iso.getOrder());
                                isomorphism2.setMappings(iso.getMappings());
                                isomorphism2.setA(iso.getA());
                                for (int j = 0; j < iso.getSequence().size(); j++) {
                                    isomorphism2.addToSequence(iso.getSequence().get(j));
                                }
                                isomorphism2.setS(iso.getS());
                                for (int j = 0; j < P.getEdges().size(); j++) {
                                    if (iso.getTmp().containsKey(j)) {
                                        if (j != i) {
                                            isomorphism2.addToTmp(j, iso.getTmp().get(j));
                                        } else {
                                            isomorphism2.addToTmp(i, e);
                                        }
                                    }
                                }
                                toAdd.add(isomorphism2);
                            }
                        }
                    }
                    for (int j = iso.getOrder(); j < P.getEdges().size(); j++) {
                        if (iso.getTmp().containsKey(j)) {
                            if (iso.getTmp().get(j).getT() < e.getT()) {
                                iso.removeFromTmp(j);
                            } else if (j == iso.getOrder()) {
                                iso.addToSequence(iso.getTmp().get(j));
                                iso.setOrder(j + 1);
                                iso.removeFromTmp(j);
                            }
                        }
                    }
                }
                tempIso.removeAll(toRemove);
                tempIso.addAll(toAdd);
            }
            //   }
            //  System.out.println("Verifying isomorphisms");
            for (Isomorphism iso : tempIso) {
                if (iso.getOrder() == P.getEdges().size()) {
                    isomorphisms.add(iso);
                }
            }
            //  System.out.println("Found "+isomorphisms.size()+" isomorphisms so far");
            System.out.println("Computed clique n°" + count + "/" + cliques.size());
            count++;
            //  isomorphisms.addAll(tempIso);

        }
        return isomorphisms;
    }

    static boolean isClique(List<Vertex> clique, StaticGraph G) {
        for (Vertex x : clique) {
            for (Vertex y : clique) {
                if (!x.equals(y)) {
                    Optional<StaticEdge> o = G.getEdges().stream().filter(e -> (e.getU().equals(x) && e.getV().equals
                            (y)) || (e.getU().equals(y) && e.getV().equals(x))).findFirst();

                    if (!o.isPresent()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}