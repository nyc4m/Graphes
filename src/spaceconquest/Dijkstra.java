/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceconquest;

import java.util.ArrayList;

/**
 *
 * @author as814738
 */
public class Dijkstra {

    private ArrayList<Boolean> mark;
    private ArrayList<Integer> pi;
    private ArrayList<Integer> d;
    private Graphe G;

    /**
     * Constructeur qui initialise les ArrayList
     */
    public Dijkstra(Graphe g) {
        mark = new ArrayList();
        for (int i = 1; i < mark.size(); i++) {
            mark.add(false);
        }

        pi = new ArrayList();
        for (int i = 1; i < pi.size(); i++) {
            pi.add(-1);
        }

        d = new ArrayList();
        d.set(1, 0);
        for (int i = 2; i < d.size(); i++) {
            d.add(d.size() + 1);
        }

        this.G = g;
    }

    public void initialisation() {
        mark = new ArrayList();
        for (int i = 1; i < mark.size(); i++) {
            mark.add(false);
        }

        pi = new ArrayList();
        for (int i = 1; i < pi.size(); i++) {
            pi.add(-1);
        }

        d = new ArrayList();
        d.set(1, 0);
        for (int i = 2; i < d.size(); i++) {
            d.add(d.size() + 1);
        }
    }

    public void relachement(int a, int b) {

        if (G.getMatrice(a, b) != 0) {

            if (d.get(b) > d.get(a) + G.getMatrice(a, b)) {
                d.set(b, d.get(a) + G.getMatrice(a, b));
                pi.set(b, a);

            }

        }

    }

    /*
    * Ici , faut faire ça (a ← le sommet non marqu´e de d[a] minimum)
     */
    public Integer sommetNonMarqué() {

        int res;

        for (int i = 1; i < d.size(); i++) {

            int sommet;
            sommet = G.getNbSommet();

            if (0 == 0) {

            }
            return i;
        }
        return null;

    }

    public void Algo(Graphe g) {
        initialisation();
        int a = 1;
        while (mark != null) {
            a = d.get(a);
            mark.set(a, Boolean.TRUE);
            for (int b = 1; b < g.getNbSommet(); b++) {
                relachement(a, b);

            }
        }

    }
}
