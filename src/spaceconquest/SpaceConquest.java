/*
 * Classe principale du projet
 */
package spaceconquest;

import spaceconquest.ObjetCeleste.*;
import spaceconquest.Parties.Mode;
import spaceconquest.Partie;
import spaceconquest.Race.Race;

/**
 *
 * @author simonetma
 */
public class SpaceConquest {

    private static Partie partie;

    public static Race getTour() {
        return partie.getTour();
    }

    public static Mode getMode() {
        return partie.getMode();
    }

    public static void tourSuivant() {
        partie.tourSuivant();
    }

    public static void main(String[] args) {
        //on cree la partie
        partie = new Partie(5);

        /*//ajout des éléments clé de la partie
        partie.placerLicoLand(2, 2);
        partie.placerLicoShip(5, 5);
        partie.placerZombificator(10, 3);
        
         *///placement des objets célestes
        partie.placerObjetCeleste(new Etoile(), 1, 2);
        /*partie.placerObjetCeleste(new Asteroide(), 4, 4);
        partie.placerObjetCeleste(new Asteroide(), 5, 4);
        partie.placerObjetCeleste(new Asteroide(), 5, 3);
                
        //on definit le mode de jeu
        partie.setMode(Mode.manuel);*/
        //on lance l'IHM
        partie.setMode(Mode.automatique);
        partie.placerLicoLand(12, 2);
        partie.placerLicoShip(1, 1);
        System.out.println(partie.getCarte().getSoleil().getX() + " " + partie.getCarte().getSoleil().getX());
        //System.out.println(partie.getCarte().getGrapheGrille());
        Dijkstra d = new Dijkstra(partie.getCarte().getGrapheZombie());
        d.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getLicoShip()), partie.getCarte().getSoleilInt());
        d.afficheTableaux();
        partie.start();
    }

}