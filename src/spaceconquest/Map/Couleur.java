/*
 * Couleur de fond des case
 */
package spaceconquest.Map;

import java.awt.Color;

/**
 *
 * @author simonetma
 */
public enum Couleur {
    Rouge(new Color(255,0,0,60)),
    Vert(new Color(0,255,0,60)),
    Jaune(new Color(255,255,0,60)),
    Blanc(new Color(255,255,255,40));
    
    private Color couleur;
    
    //constructeur enuméré
    Couleur(Color col) {
        this.couleur = col;
    }
    
    //getteur
    public Color getCouleur() {
        return this.couleur;
    }
    
}
