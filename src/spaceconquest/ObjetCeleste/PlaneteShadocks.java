/*
 * La planete des shadocks
 */
package spaceconquest.ObjetCeleste;

/**
 *
 * @author raphaelPinto
 */
public class PlaneteShadocks extends ObjetCeleste {
    
    //constructeur
    public PlaneteShadocks() {
        super(GestionnaireImage.getInstance().getImagePlaneteShadock(),0.75);
    }

    @Override
    public String getType() {
        return "planete des shadocks";
    }
}
