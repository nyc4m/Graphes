/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceconquest.IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author simonetma
 */
public class PanelSide extends JPanel {
    private JPanel fenetre;                                                     //fenetre contenant ce panel
    private PanelJoueur panelJoueur;                                            //panel d'affichage du joueur
    
    //constructeur
    public PanelSide(JPanel _fenetre) {
        this.fenetre = _fenetre;
        int taille = Math.min(this.fenetre.getHeight(),3*this.fenetre.getWidth()/4);
        int x = (this.fenetre.getWidth()-taille)/2;
        int y = (this.fenetre.getHeight()-taille)/2;
        this.setBounds(x,y,taille/3,taille);
        this.setLayout(new GridLayout(0,1));
        
    }
    
    //gestion de l'affichage
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if(this.fenetre != null) {
            //responsive
            int taille = Math.min(this.fenetre.getHeight(),3*this.fenetre.getWidth()/4);
            int x = (this.fenetre.getWidth()+2*taille/3)/2;
            int y = (this.fenetre.getHeight()-taille)/2;
            this.setBounds(x,y,taille/3,taille);
            
            if(this.panelJoueur == null) {
                this.panelJoueur = new PanelJoueur(this);
                this.add(this.panelJoueur);
            }
            this.paintBordure(g,taille);
        }
    }
    
    //affichage de la bordure
    private void paintBordure(Graphics g,int taille) {
        g.setColor(Color.gray);
        int rapport = 100;
        g.fillRect(0, 0, taille/(3*rapport), taille);
        g.fillRect(taille/3-taille/(3*rapport), 0, taille/3, taille);
        g.fillRect(0, taille-taille/(3*rapport), taille/3, taille);
    }
    
    
}
