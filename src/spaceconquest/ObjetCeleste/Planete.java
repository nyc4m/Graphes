/*
 * Une planete
 */
package spaceconquest.ObjetCeleste;

/**
 *
 * @author simonetma
 */
public class Planete extends ObjetCeleste {
    
    //constructeur
    public Planete() {
        super(GestionnaireImage.getInstance().getImagePlanete(),0.75);
    }

    @Override
    public String getType() {
        return "planete";
    }
}
