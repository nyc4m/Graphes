/*
 * Un astéroide
 */
package spaceconquest.ObjetCeleste;

/**
 *
 * @author simonetma
 */
public class Asteroide extends ObjetCeleste {

    //constructeur
    public Asteroide() {
        super(GestionnaireImage.getInstance().getImageAsteroide(),1.25);
    }

    @Override
    public String getType() {
        return "asteroide";
    }
    
}
