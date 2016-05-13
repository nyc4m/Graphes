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
     
    }

}
