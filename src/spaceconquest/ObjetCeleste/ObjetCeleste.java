/*
 * Classe abstraite des objets célèstes
 */
package spaceconquest.ObjetCeleste;

import java.awt.image.BufferedImage;
import spaceconquest.Map.Couple;

/**
 *
 * @author simonetma
 */
public abstract class ObjetCeleste{
    private BufferedImage Image;                                                //image de l'objet
    private double rTaille;                                                     //rapport d'affichage
    private Couple position;                                                    //position de l'objet dans la map
    
    //constructeur creux 
    public  ObjetCeleste(){}; 
    
    //constructeur privée des classes filles
    protected ObjetCeleste(BufferedImage _Image, double _rTaille) {
        this.Image = _Image;
        this.rTaille = _rTaille;
        this.position = null;
    }
    
    //setteur de la position de l'objet dans la carte
    public void setPosition(Couple c) {
        this.position = c;
    }
    
    //getteur de la position
    public Couple getPosition() {
        return this.position;
    }
    
    //getteur de son rapport d'affichage
    public double getRTaille() {
        return rTaille;
    }

    //renvoie le type de corp céleste dont il s'agit
    public abstract String getType();
    
    //getteur de son image
    public BufferedImage getImage() {
        return this.Image;
    }
}
