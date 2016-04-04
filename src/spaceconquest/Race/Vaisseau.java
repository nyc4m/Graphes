/*
 * vaisseaux
 */
package spaceconquest.Race;

import java.awt.image.BufferedImage;
import spaceconquest.Map.Couple;
import spaceconquest.ObjetCeleste.GestionnaireImage;

/**
 *
 * @author simonetma
 */
public class Vaisseau {
    private final Race race;                                                    //race du vaisseau
    private Couple position;                                                    //position du vaisseau
    
    //constructeur
    public Vaisseau(Race r) {
        this.race = r;
        position = null;
    }
    
    //setteur de la position
    public void setPosition(Couple c) {
        this.position = c;
    }
    
    //getteur de la race
    public Race getRace() {
        return this.race;
    }
    
    //getteur de la position du vaisseau dans la map
    public Couple getPosition() {
        return this.position;
    }
    
    //affichage du nom du vaisseau
    @Override
    public String toString() {
        switch(race) {
            case Zombie : return "croiseur Zombie";
            case Licorne : return "vaisseau amiral Licorne";
        }
        return "vaisseau inconnu";
    }
    
    //getteur de l'image du vaisseau
    public BufferedImage getImage() {
        return GestionnaireImage.getInstance().getImageVaisseau(race);
    }
}
