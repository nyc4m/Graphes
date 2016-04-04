/*
 * Panel affichant le portrait du joueur dont c'est le tour
 */
package spaceconquest.IHM;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import spaceconquest.ObjetCeleste.GestionnaireImage;
import spaceconquest.SpaceConquest;

/**
 *
 * @author simonetma
 */
public class PanelJoueur extends JPanel {
    private final PanelSide panelSide;                                          //panel latéral contenant ce panel
    
    //constructeur
    public PanelJoueur(PanelSide panel) {
        this.panelSide = panel;
        this.setBackground(Color.white);
        this.setBounds(0,0,panelSide.getWidth(),panelSide.getWidth());
    }
    
    //affichage de la bordure
    private void paintBordure(Graphics g) {
        g.setColor(Color.gray);
        int rapport = 100;
        g.fillRect(0, 0, panelSide.getWidth()/rapport,  panelSide.getWidth());
        g.fillRect(0,0,panelSide.getWidth(),panelSide.getWidth()/rapport);
        g.fillRect((rapport-1)*panelSide.getWidth()/rapport,0,panelSide.getWidth(),panelSide.getWidth());
        g.fillRect(0,(rapport-1)*panelSide.getWidth()/rapport,panelSide.getWidth(),panelSide.getWidth());
    }
    
    //gestion de l'affichage
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.panelSide != null) {
            this.setBounds(0,0,panelSide.getWidth(),panelSide.getWidth());
            //affichage de l'image du joueur
            g.drawImage(GestionnaireImage.getInstance().getImageJoueur(SpaceConquest.getTour()), 0, 0,panelSide.getWidth(),panelSide.getWidth(), this);
            this.paintBordure(g);
            
        }
    }
}
