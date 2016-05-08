/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.ArrayList;
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
        private ArrayList<Integer> cheminZombies;
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
            numTourZombie = 2;
            
             licorne = new Dijkstra(partie.getCarte().getGrapheLicornes());
             zombie = new Dijkstra(partie.getCarte().getGrapheZombie());
           
            licorne.plusCourtChemin(posVaisseauLicornes, partie.getCarte().getSoleilInt());
            
           

            this.cheminLicornes = new ArrayList();
            this.cheminZombies = new ArrayList();
            this.cheminLicornes = licorne.construireChemin(posVaisseauLicornes, posLicoLand);
            zombie.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getZombificator()), partie.getCarte().getSoleilInt());           
            
                                          
                                
                
                

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
                
                zombie = new Dijkstra(partie.getCarte().getGrapheZombie());
                zombie.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getZombificator()),  partie.getCarte().getSoleilInt());                     
               
                this.cheminZombies = zombie.construireChemin(partie.getCarte().getPosVaisseauInt(partie.getZombificator()), partie.getCarte().getPosVaisseauInt(partie.getLicoShip()));
                
                Couple caseActuelle = partie.getCarte().getCouple(this.cheminZombies.get(numTourZombie-2 ), this.partie.getCarte().getTaille());
                Couple prochaineCase = partie.getCarte().getCouple(this.cheminZombies.get(numTourZombie -1), this.partie.getCarte().getTaille());
                partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Jaune);
                partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
                partie.getZombificator().setPosition(prochaineCase);
               
              

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
