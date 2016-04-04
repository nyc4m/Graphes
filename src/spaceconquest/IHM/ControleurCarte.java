/*
 * Controleur du panelCarte
 */
package spaceconquest.IHM;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import spaceconquest.Map.Couple;

/**
 *
 * @author simonetma
 */

public class ControleurCarte implements MouseListener {

    private final PanelCarte panel;                                                   //lien vers la vue
    
    //constructeur
    public ControleurCarte(PanelCarte _panel) {
        super();
        this.panel = _panel;
    }
    
    //Action lors du click de la souris
    @Override
    public void mouseClicked(MouseEvent me) {
        //on repère si on est dans un des polygons
        Couple c = this.positionClick(me);
        if(c != null) {
            this.panel.selectionCase(c);
        }
    }
    
    //renvoie la position de la case sur laquelle le joueur à cliqué
    private Couple positionClick(MouseEvent me) {
        for(Polygon p:this.panel.getPolygons().keySet()) {
            if(p.contains(me.getPoint())) {
                return this.panel.getPolygons().get(p);
            }
        }
        return null;
    }

    //autres évènements non utilisés
    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
}
