/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import spaceconquest.Map.Couleur;
import spaceconquest.Map.Couple;
import spaceconquest.Parties.Mode;
import spaceconquest.Race.Vaisseau;
import spaceconquest.ObjetCeleste.Gagner;
import spaceconquest.Race.Race;

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
        private ArrayList<Integer> cheminShadock;

        private Random randomGenerator;

        /**
         * contient le chemin le plus court pour aller sur la planete des
         * licornes
         */
        private ArrayList<Integer> cheminShadocks;

        /**
         * contient le chemin le plus court pour aller sur la planete des
         * licornes
         */
        private ArrayList<Integer> sommetInter;
        /**
         * Contient le plus court chemin des shadocks
         */
        private Dijkstra shadock;

        /**
         * Contient le numero du sommet de la planete des licornes
         */
        int posLicoland;
        /**
         * Contient le numero du sommet de la planete des shadocks
         */
        int posShadockPlanete;

        private ArrayList<Integer> case_a_eviter;

        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;

            shadock = new Dijkstra(partie.getCarte().getGrapheLicornes());
            cheminShadock = shadock.cheminShadock(partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY()), partie.getCarte().getSoleilInt());

            posLicoland = partie.getCarte().position(partie.getLicoLand().getPosition().getX(), partie.getLicoLand().getPosition().getY());
            posShadockPlanete = partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY());
            this.case_a_eviter = new ArrayList();
        }

        /**
         * Vide la liste des cases accessibles aux shadocks uniquement si la
         * liste contient quelque chose
         */
        public void viderListeGibi() {
            if (!this.case_a_eviter.isEmpty()) {
                this.case_a_eviter.clear();
            }
        }

        /**
         * Stocke tout les sommets accessibles aux shadocks dans le but de les
         * isoler des licornes
         */
        public void optiGibit() {

            this.viderListeGibi();
            System.out.println(posLicoland);
            Dijkstra sha = new Dijkstra(partie.getCarte().getGrapheLicornes());
            sha.cheminShadock(partie.getCarte().getPosVaisseauInt(partie.getShadocks()), partie.getCarte().getSoleilInt());
            for (int i = 0; i <= sha.getSommets().size() - 1; i++) {
                if (sha.getDistances().get(i) <= 2 && sha.getSommets().get(i) != partie.getCarte().getPosVaisseauInt(partie.getLicoShip()) && sha.getSommets().get(i) != posLicoland) {
                    this.case_a_eviter.add(sha.getSommets().get(i));
                }
            }
            System.out.println(this.case_a_eviter);

        }

        private void majListePlanete() {
            Couple licorne = this.partie.getLicoShip().getPosition();
            Couple planete_a_supprimer = null;
            for (Couple e : this.partie.getCarte().getLicoLands()) {
                if (licorne.hashCode() == e.hashCode()) {
                    planete_a_supprimer = e;
                }
            }
            if (planete_a_supprimer != null) {
                this.partie.getCarte().getLicoLands().remove(planete_a_supprimer);
            }
        }

        public int planeteProche() {
            this.majListePlanete();
            Dijkstra d = new Dijkstra(this.partie.getCarte().getGrapheLicornes());
            d.plusCourtChemin(this.partie.getCarte().getPosVaisseauInt(partie.getLicoShip()), this.partie.getCarte().getSoleilInt());
            int distanceMin = d.infini(); //la distance la plus courte est 0 pour commencer;
            int planete = -1;
            int _case = 0;
            for (Couple e : this.partie.getCarte().getLicoLands()) {
                _case = this.partie.getCarte().position(e.getX(), e.getY());
                if (d.getDistances().get(_case) < distanceMin) {
                    distanceMin = d.getDistances().get(_case);
                    planete = _case;
                }
            }

            return _case;
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
                    case Shadocks:
                        this.tourDesShadocks();
                        break;
                }
                this.partie.tourSuivant();
            }
        }

        //ce qu'il se passe lors du tour des shadocks
        private void tourDesShadocks() {
            System.out.println("Tour des Shadocks !");

            if (this.partie.getModeAuto() == true) {

                boolean fini = false;
                Couple caseActuelle = partie.getShadocks().getPosition();

                int posShadockVaisseau = partie.getCarte().getPosVaisseauInt(partie.getShadocks());

                partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Rouge);

                while (fini != true) {
                    randomGenerator = new Random();
                    int index = randomGenerator.nextInt(cheminShadock.size());
                    int sommet = cheminShadock.get(index);

                    Dijkstra d = new Dijkstra(partie.getCarte().getGrapheZombie());
                    d.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getShadocks()), partie.getCarte().getSoleilInt());

                    if (d.getDistances().get(sommet) <= 1 && sommet != posShadockPlanete && sommet != posShadockVaisseau && sommet != posLicoland) {

                        Couple prochaineCase = partie.getCarte().getCouple(sommet, partie.getCarte().getTaille());
                        partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
                        partie.getShadocks().setPosition(prochaineCase);

                        fini = true;

                    }
                }

            }
        }

        //ce qu'il se passe lors du tour des zombies
        private void tourDesZombies() {
            System.out.println("Tour des Zombies !");
            if (this.partie.getModeAuto() == true) {

                int licornes = this.partie.getCarte().getPosVaisseauInt(this.partie.getLicoShip());
                this.deplacement(this.partie.getCarte().getGrapheZombie(), this.partie.getZombificator(), licornes);

                /* Stop la partie lors du gagnant pour permet un arret des autres joueurs. */
                Couple posLicornes = this.partie.getLicoShip().getPosition();
                Couple posZombies = this.partie.getZombificator().getPosition();
                if (posLicornes.hashCode() == posZombies.hashCode()) {
                    stop();
                    System.out.println("Les Zombies ont gagné");
                }

                this.partie.refreshCarte();

            }
        }

        /**
         * Méthode permettant de marquer la couleur de la case où se trouve le
         * vaisseau
         *
         * @param c Position à colorier
         */
        public void marquerCouleur(Couple c) {
            switch (this.partie.getTour()) {
                case Licorne:
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Vert);
                    break;
                case Zombie:
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Jaune);
                    break;
                case Shadocks:
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Rouge);
            }
        }

        private void deplacerVaisseau(Couple prochaineCase) {
            switch (this.partie.getTour()) {
                case Licorne:
                    this.partie.getLicoShip().setPosition(prochaineCase);
                    break;
                case Zombie:
                    this.partie.getZombificator().setPosition(prochaineCase);
                    break;
                case Shadocks:
                    this.partie.getShadocks().setPosition(prochaineCase);
            }
        }

        /**
         * Métthode permettant le déplacement des lciornes et des zombies
         *
         * @param graphe prend le graphe de la race correspondante
         * @param v Le vaisseau actuelle
         * @param cible L'objectif du vaisseau
         */
        public void deplacement(Graphe graphe, Vaisseau v, int cible) {

            Couple caseActuelle = null;
            Dijkstra chemin = new Dijkstra(graphe);
            Couple prochaineCase;
            ArrayList<Integer> pcChemin;

            if (SpaceConquest.getTour() == Race.Licorne) {

                this.optiGibit();
                chemin.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getLicoShip()), partie.getCarte().getSoleilInt(), partie.getCarte().getPosVaisseauInt(partie.getZombificator()), this.case_a_eviter);
                caseActuelle = partie.getCarte().getCouple(partie.getCarte().getPosVaisseauInt(partie.getLicoShip()), this.partie.getCarte().getTaille());
                pcChemin = chemin.construireChemin(this.partie.getCarte().getPosVaisseauInt(v), cible);

            } else {

                chemin.plusCourtChemin(this.partie.getCarte().getPosVaisseauInt(v), this.partie.getCarte().getSoleilInt());
                caseActuelle = partie.getCarte().getCouple(partie.getCarte().getPosVaisseauInt(partie.getZombificator()), this.partie.getCarte().getTaille());
                pcChemin = chemin.construireChemin(this.partie.getCarte().getPosVaisseauInt(v), cible);

            }

            if (pcChemin.size() == 1) {
                prochaineCase = null;
            } else {
                prochaineCase = partie.getCarte().getCouple(pcChemin.get(1), this.partie.getCarte().getTaille());
            }

            this.marquerCouleur(caseActuelle);

            this.partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);

            this.deplacerVaisseau(prochaineCase);

            if (SpaceConquest.getTour() == Race.Licorne) {
                partie.getLicoShip().setPosition(prochaineCase);
            } else {
                partie.getZombificator().setPosition(prochaineCase);
            }

            partie.refreshCarte();

        }

        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {
            System.out.println("Tour des Licornes !");

            if (this.partie.getModeAuto() == true) {

                int planete_la_plus_proche = this.planeteProche();
                if (planete_la_plus_proche != 0) {
                    this.deplacement(this.partie.getCarte().getGrapheLicornes(), this.partie.getLicoShip(), planete_la_plus_proche);
                }else{
                    SpaceConquest.tourSuivant();
                }
                
                if (this.partie.getCarte().getLicoLands().isEmpty()) {
                    stop();
                    System.out.println("Les licornes ont gagné.");

                    /* Supplément pour le beau jeu  et surtout l'affichage
                    for(int i=1; i < (partie.getCarte().getTaille())*3;i++){
                        for(int j=1; j < partie.getCarte().getTaille()+1;j++){
                    partie.getCarte().addObjetCeleste(new Gagner(), i, j);
                      }
                    }*/
                }

            }

        }

    }
}
