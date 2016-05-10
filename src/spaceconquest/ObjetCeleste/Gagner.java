/*
 * La planete des shadocks
 */
package spaceconquest.ObjetCeleste;

/**
 *
 * @author raphaelPinto
 */
public class Gagner extends ObjetCeleste {
    
    //constructeur
    public Gagner() {
        super(GestionnaireImage.getInstance().getImageGagner(),0.75);
    }

    @Override
    public String getType() {
        return "c'est la victoire";
    }
}
