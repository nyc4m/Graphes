/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;
import spaceconquest.Map.Case;
import spaceconquest.Map.Couleur;
import spaceconquest.Map.Couple;
import spaceconquest.Parties.Mode;

/**
 *
 * @author simonetma
 */
public class TimerPartie extends Timer {

    private Partie partie;        //partie en cours

    //constructuer    
    public TimerPartie(Partie partie) {
        super();
        this.partie = partie;
        if (this.partie.getMode() == Mode.automatique) {

            this.scheduleAtFixedRate(new TimerTaskPartie(this.partie), 0, 1000);

        }
    }

    //arret du timer si besoin
    public void stop() {
        this.cancel();
    }

    //sous classe privée
    private class TimerTaskPartie extends TimerTask {

        private Partie partie;

        /**
         * contient le chemin le plus court pour atteindre les licornes
         */
        private HashMap<Couple, ArrayList> cheminZombies;
        /**
         * contient le chemin le plus court pour aller sur la planete des
         * licornes
         */
        private ArrayList<Integer> cheminLicornes;
        /**
         * Le numero du tour des licornes
         */
        private int numEtapeLicorne;
        /**
         * le numero du tour des zombie
         */
        private int numTourZombie;
        /**
         * Contient le Dijkstra des différentes races
         */
        private Dijkstra licorne;
        private Dijkstra zombie;

        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;

            int posVaisseauLicornes = partie.getCarte().getPosVaisseauInt(partie.getLicoShip());
            int posVaisseauZombies = partie.getCarte().getPosVaisseauInt(partie.getZombificator());
            int posLicoLand = partie.getCarte().position(partie.getLicoLand().getPosition().getX(), partie.getLicoLand().getPosition().getY());

            numEtapeLicorne = 2;

            licorne = new Dijkstra(partie.getCarte().getGrapheLicornes());

            licorne.plusCourtChemin(posVaisseauLicornes, partie.getCarte().getSoleilInt());

            this.cheminLicornes = new ArrayList();

            this.cheminLicornes = licorne.construireChemin(posVaisseauLicornes, posLicoLand);
            this.cheminZombies = this.cheminsZombies();

        }

        public HashMap<Couple, ArrayList> cheminsZombies() {
            HashMap<Couple, ArrayList> res = new HashMap();
            for (int i = 1; i <= this.partie.getCarte().getGrapheZombie().getNbSommet(); i++) {
                for (int j = 1; j <= this.partie.getCarte().getGrapheZombie().getNbSommet(); j++) {
                    if (i != this.partie.getCarte().getSoleilInt()) {
                        Couple c = new Couple(i, j);
                        Dijkstra chemin = new Dijkstra(this.partie.getCarte().getGrapheZombie());
                        chemin.plusCourtChemin(i, this.partie.getCarte().getSoleilInt());
                        res.put(c, chemin.construireChemin(i, j));
                        int sommet = j + (i * this.partie.getCarte().getTaille()) - this.partie.getCarte().getTaille();
                        System.out.println("sommet = " + sommet);
                        System.out.println("  chemin = " + chemin.afficherChemin(i, j));
                    }
                }
            }
            boolean ok = true;
            return res;
        }

        //fonction appellée à chaque tic du timer
        @Override
        public void run() {
            if (this.partie.isIHMReady()) {
                switch (this.partie.getTour()) {
                    case Licorne:
                        this.tourDesLicornes();
                        break;
                    case Zombie:
                        this.tourDesZombies();
                        break;
                }
                this.partie.tourSuivant();
            }
        }

        //ce qu'il se passe lors du tour des zombies
        private void tourDesZombies() {
            System.out.println("Tour des Zombies !");
            if (this.partie.getModeAuto() == true) {

                Couple c = new Couple(this.partie.getCarte().getPosVaisseauInt(this.partie.getZombificator()), this.partie.getCarte().getPosVaisseauInt(this.partie.getLicoShip()));
                System.out.println("c = " + c.getX() + "/" + c.getY());
                ArrayList<Integer> chemin = this.cheminZombies.get(c);
                Couple caseActuelle = partie.getCarte().getCouple(chemin.get(0), this.partie.getCarte().getTaille());
                Couple prochaineCase = partie.getCarte().getCouple(chemin.get(1), this.partie.getCarte().getTaille());
                this.partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Jaune);
                this.partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
                this.partie.getZombificator().setPosition(prochaineCase);

            }
        }

        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {
            System.out.println("Tour des Licornes !");
            if (this.partie.getModeAuto() == true) {

                Couple caseActuelle = partie.getCarte().getCouple(this.cheminLicornes.get(numEtapeLicorne - 2), this.partie.getCarte().getTaille());
                Couple prochaineCase = partie.getCarte().getCouple(this.cheminLicornes.get(numEtapeLicorne - 1), this.partie.getCarte().getTaille());
                partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Vert);
                partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
                partie.getLicoShip().setPosition(prochaineCase);
                this.numEtapeLicorne++;

            }

            partie.refreshCarte();
        }

    }
}
