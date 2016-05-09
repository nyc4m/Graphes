/*
 * Classe principale du projet
 */
package spaceconquest;

import java.util.ArrayList;
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

        partie.setMode(Mode.automatique);
        
        partie.placerLicoShip(1, 2);
        partie.placerLicoLand(15, 3);
        
        partie.placerZombificator(1, 5);
        partie.getCarte().addObjetCeleste(new Etoile(), 3, 2);
        
        partie.placerShadocksShip(10, 1);
        partie.placerShadocksLand(10, 2);
        
        partie.start();
    }

}
