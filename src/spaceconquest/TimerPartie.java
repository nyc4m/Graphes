/*
 * Timer pour le mode automatique
 */
package spaceconquest;

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

        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;
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

            }
        }

        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {
            System.out.println("Tour des Licornes !");

            if (this.partie.getModeAuto() == true) {

                Couple c = new Couple(partie.getLicoShip().getPosition().getX(), partie.getLicoShip().getPosition().getY());

               

                Dijkstra d = new Dijkstra(partie.getCarte().getGrapheLicornes());

                int planeteLico = partie.getCarte().position(partie.getLicoLand().getPosition().getX(), partie.getLicoLand().getPosition().getY());
                int vaisseauLico = partie.getCarte().getPosVaisseauInt(partie.getLicoShip());
                d.plusCourtChemin(vaisseauLico, partie.getCarte().getSoleilInt());

                if (d.getAntecedents().get(planeteLico) != vaisseauLico) {

                    if (d.getDistances().get(planeteLico) > 2) {
                        Dijkstra e = new Dijkstra(partie.getCarte().getGrapheLicornes());

                        e.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getLicoShip()), partie.getCarte().getSoleilInt());
                        int i = e.getAntecedents().get(planeteLico);
                       

                        boolean fin = false;

                        while (fin != true) {
                            partie.refreshCarte();

                            if (d.getDistances().get(i) <= 2) {
                                partie.getCarte().BougerVaisseau(partie.getCarte().getCouple(vaisseauLico, partie.getCarte().getTaille()), partie.getCarte().getCouple(i, partie.getCarte().getTaille()));
                                partie.placerLicoShipCouple(partie.getCarte().getCouple(i, partie.getCarte().getTaille()));
                                fin = true;
                            }

                            if (d.getDistances().get(i) > 2) {
                                i = d.getAntecedents().get(i);

                            }

                        }

                    }
                     if (d.getDistances().get(planeteLico) <= 2) {
                          partie.getCarte().BougerVaisseau(partie.getCarte().getCouple(vaisseauLico, partie.getCarte().getTaille()), partie.getCarte().getCouple(planeteLico, partie.getCarte().getTaille()));
                     }

                }
                
                partie.getCarte().getCase(c).setCouleur(Couleur.Vert);
            partie.refreshCarte();
            }
          
        }
    }
}
