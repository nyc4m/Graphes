/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;
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
        private ArrayList<Integer> cheminShadock;
        Dijkstra shadock;
        private Random randomGenerator;

        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;

           
            
            shadock = new Dijkstra(partie.getCarte().getGrapheLicornes());
            cheminShadock = new ArrayList();
            cheminShadock = shadock.cheminShadock(partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY()), partie.getCarte().getSoleilInt());
            
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
                int posShadockPlanete = partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY());
                int posShadockVaisseau = partie.getCarte().getPosVaisseauInt(partie.getShadocks());
                partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Rouge);

                while (fini != true) {
                    randomGenerator = new Random();
                    int index = randomGenerator.nextInt(cheminShadock.size());
                    int sommet = cheminShadock.get(index);
                    
                    Dijkstra d = new Dijkstra(partie.getCarte().getGrapheGrille());
                    d.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getShadocks()), partie.getCarte().getSoleilInt());
                    
                    
                    if (d.getDistances().get(sommet) <= 1 && sommet != posShadockPlanete && sommet != posShadockVaisseau) {
                  
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

            }
        }

        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {
            System.out.println("Tour des Licornes !");

            if (this.partie.getModeAuto() == true) {
                
            }
            partie.refresh();

        }
    }
}
