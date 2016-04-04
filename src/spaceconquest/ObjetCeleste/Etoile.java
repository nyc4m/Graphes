/*
 * Une étoile
 */
package spaceconquest.ObjetCeleste;

/**
 *
 * @author simonetma
 */
public class Etoile extends ObjetCeleste {
    
    //constructeur
    public Etoile() {
        super(GestionnaireImage.getInstance().getImageEtoile(),1.2);
    }

    @Override
    public String getType() {
        return "etoile";
    }
}
