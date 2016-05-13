/*
 * Classe principale du projet
 */
package spaceconquest;

import java.io.BufferedWriter;
import java.io.FileWriter;
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


        partie.setMode(Mode.manuel);
        
        partie.placerLicoShip(8, 3);
        partie.placerObjetCeleste(new Asteroide(), 7, 2);
        partie.placerObjetCeleste(new Asteroide(), 6, 3);
        partie.placerObjetCeleste(new Asteroide(), 7, 3);
        partie.placerLicoLand(15, 3);
        partie.placerLicoLand(4, 5);
        
        partie.placerZombificator(15, 1);
        partie.getCarte().addObjetCeleste(new Etoile(), 3, 2);
        
        partie.placerShadocksShip(8, 3);
        partie.placerShadocksLand(10, 2);
        

        
        
        partie.start();
        
        try{
            BufferedWriter matrice = new BufferedWriter(new FileWriter("c:\\users\\baptiste\\desktop\\matrice.html"));
            BufferedWriter matriceZ = new BufferedWriter(new FileWriter("c:\\users\\baptiste\\desktop\\matriceZ.html"));
            matriceZ.write(partie.getCarte().getGrapheZombie().toString());
            matrice.write(partie.getCarte().getGrapheLicornes().toString());
            matriceZ.close();
            matrice.close();
            System.out.println("Ecrit");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
