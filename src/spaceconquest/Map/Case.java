/*
 * Modélisation d'une case de la carte
 */
package spaceconquest.Map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import spaceconquest.ObjetCeleste.ObjetCeleste;
import spaceconquest.Race.Vaisseau;

/**
 *
 * @author simonetma
 */
public class Case {
    private Couleur couleur;                                                    //couleur du background
    private ObjetCeleste objetCeleste;                                          //objet céleste présent dans la case
    private Vaisseau vaisseau;                                                  //vaisseau présent dans la case
    
    //constructeur
    public Case() {
        this.couleur = Couleur.Blanc;
        this.objetCeleste = null;
        this.vaisseau = null;
    }
    
    
    //ajoute un objet céleste dans la case
    public void addObjetCeleste(ObjetCeleste obj) {
        this.objetCeleste = obj;
    }
    
    //place un vaisseau dans la case
    public void addVaisseau(Vaisseau v) {
        this.vaisseau = v;
    }
    
    //getteur du vaisseau présent dans la case
    public Vaisseau getVaisseau() {
        return this.vaisseau;
    }
	
	//getteur de l'objet celeste 
    public ObjetCeleste getObjetCeleste() {
        return this.objetCeleste;
    }
    
    //getteur de l'image de l'objet céleste
    public BufferedImage getImageObjetCeleste() {
        if(this.objetCeleste == null) {
            return null;
        }
        else {
            return this.objetCeleste.getImage();
        }
    }
    
    //getteur du rapport d'affichage de l'objet céleste
    public double getRTailleObjetCeleste() {
        if(this.objetCeleste == null) {
            return 0;
        }
        else {
            return this.objetCeleste.getRTaille();
        }
    }
    
    //getteur de la couleur de fond
    public Color getCouleur() {
        return this.couleur.getCouleur();
    }
    
    //setteur de la couleur de fond
    public void setCouleur(Couleur c) {
        this.couleur = c;
    }
    
}
