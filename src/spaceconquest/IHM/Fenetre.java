/*
 * Fenetre principale de l'IHM
 */
package spaceconquest.IHM;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import spaceconquest.Carte;

/**
 *
 * @author simonetma
 */
public class Fenetre extends JFrame {
    
    private final PanelCarte panelCarte;                                        //panel de la carte
    private final PanelSide panelSide;                                          //panel de coté
    
    //initialisation de la fenetre
    public Fenetre(Carte carte) {
        super("Space Conquest");
        this.setExtendedState(Fenetre.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(700,700));
        
        //gestion des panels
        this.setLayout(null);
        JPanel CPanel = (JPanel) this.getContentPane();
        this.panelCarte = new PanelCarte(CPanel,carte);
        this.panelSide = new PanelSide(CPanel);
        CPanel.add(this.panelCarte);
        CPanel.add(this.panelSide);
        this.setVisible(true);
        
    }
    
    //recharge la panel latéral
    public void refreshSide() {
        this.panelSide.repaint();
    }
    
    //gestion de l'affichage
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(panelCarte != null) {
            panelCarte.paint(panelCarte.getGraphics());
        }
        if(panelSide != null) {
            panelSide.paint(panelSide.getGraphics());
        }
        
    }
    
}
