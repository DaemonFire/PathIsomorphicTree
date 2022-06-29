/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */

import java.util.ArrayList;

public class Algorithm {

    static ArrayList<TemporalIsomorphism> computeTemporalIsomorphims(TemporalGraph ls, TemporalGraph p) {

        ArrayList<TemporalIsomorphism> temporalIsomorphism = new ArrayList<>();

        ArrayList<TemporalIsomorphism>[][] Iso = new ArrayList[ls.getEndInstant() - ls.getStartInstant() + 1][p
                .getEndInstant() - p.getStartInstant() + 1];

        int[] tab = KMPpreprocess(ls);

        for (int t = ls.getStartInstant(); t <= ls.getEndInstant(); t++) {
            for (int tprime = p.getStartInstant(); tprime <= p.getEndInstant(); tprime++) {

                if (tab[t] - 1 == tprime) {
                    Iso[t][tprime] = Iso[tab[t] - 1][tab[t] - 1];

                } else {

                    for (Vertex v : ls.getVertices()) {

                        ArrayList<TemporalIsomorphism> temp = new ArrayList<>();

                        if (neighbourhood(v, ls, t).size() == 0) {

                            for (Vertex vprime : p.getVertices()) {

                                if (neighbourhood(vprime, p, tprime).size() == 0) {
                                    TemporalIsomorphism i = new TemporalIsomorphism(t);
                                    i.getMappings().put(vprime, v);
                                    temp.add(i);
                                }
                            }
                        } else {
                            for (Vertex vprime : p.getVertices()) {
                                if (neighbourhood(vprime, p, tprime).size() == 1) {
                                    ArrayList<ArrayList<Vertex>> pathList = BFS(ls, t, v, pathLength(p, tprime,
                                            vprime, null), null);

                                    for (ArrayList<Vertex> path : pathList) {
                                        TemporalIsomorphism f = createIsomorphism(p, tprime, vprime, null, path, t,
                                                0, pathLength(p, tprime, vprime, null));
                                        temp.add(f);
                                    }
                                }

                            }

                        }
                        if (Iso[t][tprime].isEmpty()) {
                            Iso[t][tprime] = new ArrayList<>();
                            Iso[t][tprime].addAll(temp);
                        } else {
                            Iso[t][tprime] = isomorphismProlongation(ls, p, Iso[t][tprime], temp);
                        }
                    }
                    if (tprime != 0) {
                        Iso[t][tprime] = isomorphimIntersection(ls, p, Iso[t][tprime], Iso[t - 1][tprime - 1]);
                    }
                    if (tprime == p.getEndInstant()) {
                        temporalIsomorphism.addAll(Iso[t][tprime]);
                    }

                }

            }

        }

        return temporalIsomorphism;
    }

    static int[] KMPpreprocess(TemporalGraph ls) {

        int[] tab = new int[ls.getEndInstant() - ls.getStartInstant() + 1];

        tab[0] = 0;

        for (int t = ls.getStartInstant() + 1; t <= ls.getEndInstant(); t++) {
            boolean equals = false;

            ArrayList<TemporalEdge> et = new ArrayList<>();
            ArrayList<TemporalEdge> etab = new ArrayList<>();

            for (TemporalEdge e : ls.getEdges()) {
                if (e.getT() == t) {
                    et.add(e);
                }
                if (e.getT() == tab[t - 1]) {
                    etab.add(e);
                }
            }

            if (et.size() == etab.size()) {
                ArrayList<TemporalEdge> temp = (ArrayList<TemporalEdge>) (et.clone());

                for (TemporalEdge e : etab) {
                    temp.remove(e);
                }
                if (temp.size() == 0) {
                    equals = true;
                }
            }
            if (equals) {
                tab[t] = tab[t - 1] + 1;
            } else {
                equals = false;
                ArrayList<TemporalEdge> e0 = new ArrayList<>();
                for (TemporalEdge e : ls.getEdges()) {
                    if (e.getT() == 0) {
                        e0.add(e);
                    }
                }
                if (et.size() == e0.size()) {
                    ArrayList<TemporalEdge> temp = (ArrayList<TemporalEdge>) (et.clone());

                    for (TemporalEdge e : e0) {
                        temp.remove(e);
                    }
                    if (temp.size() == 0) {
                        equals = true;
                    }

                    if (equals) {
                        tab[t] = 1;
                    }
                    else {
                        tab[t] = 0;
                    }
                }
            }
        }

        return tab;
    }

    static ArrayList<Vertex> neighbourhood(Vertex v, TemporalGraph ls, int t) {
        ArrayList<Vertex> neighbourhood = new ArrayList<>();
        ls.getEdges().stream().filter(e -> e.getV() == v || e.getU() == v).filter(e -> e.getT() == t).forEach(e -> {
            if (e.getU() == v) {
                neighbourhood.add(e.getV());
            } else {
                neighbourhood.add(e.getU());
            }
        });
        return neighbourhood;
    }

    static ArrayList<ArrayList<Vertex>> BFS(TemporalGraph ls, int t, Vertex v, int length, Vertex u) {
        ArrayList<ArrayList<Vertex>> pathList = new ArrayList<>();

        if (length == 1) {
            ArrayList<Vertex> path = new ArrayList<>();
            path.add(v);
            pathList.add(path);
        } else {
            ArrayList<Vertex> neighbours = neighbourhood(v, ls, t);
            neighbours.remove(u);

            for (Vertex z : neighbours) {
                ArrayList<ArrayList<Vertex>> temp = BFS(ls, t, z, length - 1, v);

                for (ArrayList<Vertex> path : temp) {
                    path.add(v);
                    pathList.add(path);
                }
            }
        }

        return pathList;
    }

    static int pathLength(TemporalGraph p, int t, Vertex v, Vertex u) {
        int length = 0;
        ArrayList<Vertex> neighbours = neighbourhood(v, p, t);

        if (u == null) {
            length = 1 + pathLength(p, t, neighbours.get(0), v);
        } else {
            if (neighbours.size() == 1) {
                length = 1;
            } else {
                neighbours.remove(u);
                Vertex z = neighbours.get(0);
                length = 1 + pathLength(p, t, z, v);
            }
        }
        return length;
    }

    static TemporalIsomorphism createIsomorphism(TemporalGraph p, int tprime, Vertex vprime, Vertex u, ArrayList<Vertex>
            path, int t, int i, int length) {
        TemporalIsomorphism sol = new TemporalIsomorphism(t);

        if (i == length - 1) {
            sol.getMappings().put(vprime, path.get(i));
        } else {
            ArrayList<Vertex> neighbours = neighbourhood(vprime, p, tprime);
            neighbours.remove(u);
            Vertex z = neighbours.get(0);
            sol = createIsomorphism(p, tprime, z, vprime, path, t, i + 1, length);
            sol.getMappings().put(vprime, path.get(i));
        }

        return sol;
    }

    static ArrayList<TemporalIsomorphism> isomorphismProlongation(TemporalGraph ls, TemporalGraph p,
            ArrayList<TemporalIsomorphism> l1, ArrayList<TemporalIsomorphism> l2) {
        ArrayList<TemporalIsomorphism> sol = new ArrayList<>();

        for (TemporalIsomorphism f1 : l1) {
            for (TemporalIsomorphism f2 : l2) {
                boolean coherent = true;

                for (Vertex vprime : f1.getMappings().keySet()) {
                    if (f2.getMappings().keySet().contains(vprime) && f2.getMappings().get(vprime) != f1.getMappings
                            ().get(vprime)) {
                        coherent = false;
                    }

                }
                if (coherent) {
                    TemporalIsomorphism h = new TemporalIsomorphism(f1.getStartInstant());
                    for (Vertex vprime : f1.getMappings().keySet()) {
                        h.getMappings().put(vprime, f1.getMappings().get(vprime));
                    }
                    for (Vertex vprime : f2.getMappings().keySet()) {
                        h.getMappings().put(vprime, f2.getMappings().get(vprime));
                    }
                    sol.add(h);
                }

            }
        }

        return sol;
    }

    static ArrayList<TemporalIsomorphism> isomorphimIntersection(TemporalGraph ls, TemporalGraph p,
            ArrayList<TemporalIsomorphism> l1, ArrayList<TemporalIsomorphism> l0) {
        ArrayList<TemporalIsomorphism> temp = new ArrayList<>();

        for (TemporalIsomorphism f0 : l0) {
            boolean exists = false;
            for (TemporalIsomorphism f1 : l1) {
                boolean coherent = true;
                for (Vertex vprime : f0.getMappings().keySet()) {
                    if (f0.getMappings().get(vprime) != f1.getMappings().get(vprime)) {
                        coherent = false;
                        break;
                    }
                }
                if (coherent) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                temp.add(f0);
            }
        }

        return temp;
    }

}