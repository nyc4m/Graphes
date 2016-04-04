/*
 * Panel d'affichage de la carte
 */
package spaceconquest.IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.HashMap;
import javax.swing.JPanel;
import spaceconquest.Carte;
import spaceconquest.Map.Case;
import spaceconquest.Map.Couple;
import spaceconquest.Parties.Mode;
import spaceconquest.ObjetCeleste.GestionnaireImage;
import spaceconquest.SpaceConquest;

/**
 *
 * @author simonetma
 */
public class PanelCarte extends JPanel {
    private final JPanel fenetre;                                               //fenetre contenant le panel
    private final Carte carte;                                                  //carte à afficher
    private final HashMap<Polygon,Couple> polygons;                             //liste des hexagones dessinés (pour repérer les clicks)                    
    
    //constructeur
    public PanelCarte(JPanel _fenetre, Carte _carte) {
        this.fenetre = _fenetre;
        this.carte = _carte;
        
        //gestion dynamique de la taille
        int taille = Math.min(this.fenetre.getHeight(),3*this.fenetre.getWidth()/4);
        int x = (this.fenetre.getWidth()-4*taille/3)/2;
        int y = (this.fenetre.getHeight()-taille)/2;
        this.setBounds(x,y,taille,taille);
        this.setBackground(Color.black);
        
        //initialisation de la liste des hexagones
        this.polygons = new HashMap<>();
        
        //ajout d'un controleur pour le mode manuel
        if(SpaceConquest.getMode() == Mode.manuel) {
            this.addMouseListener(new ControleurCarte(this));
        }
    }
    
    //getteur de la liste des hexagones
    public HashMap<Polygon,Couple> getPolygons() {
        return this.polygons;
    }
    
    //transmetteur de la selection d'une case
    public void selectionCase(Couple c) {
        this.carte.selectionCase(c);
        this.repaint();
    }
    
    //gestion de l'affichage
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if(this.fenetre != null) {
            //responsive
            int taille = Math.min(this.fenetre.getHeight(),3*this.fenetre.getWidth()/4);
            int x = (this.fenetre.getWidth()-4*taille/3)/2;
            int y = (this.fenetre.getHeight()-taille)/2;
            this.setBounds(x,y,taille,taille);
           
            //dessin du fond
            try{
                g.drawImage(GestionnaireImage.getInstance().getImageFond(), 0, 0, taille, taille, this);
            }
            catch(Exception e) {
                
            }
            //dessin de la map
            int n=this.carte.getTaille();
            int tailleHexagone = taille/((6*n+3)/2);
            this.polygons.clear();
           
            for(int i=1;i<=3*n;i++) {
                int decalageX = (taille-((6*n-3)*tailleHexagone)/2)/2;
                int decalageY = (int) ((2*taille-((3*n+1)*Math.sqrt(3)*tailleHexagone))/4);   
                if(i%2 == 1) {
                    decalageX = decalageX+3*tailleHexagone/2;
                }
                for(int j=1;j<=n;j++) {
                    //dessin de l'hexagone
                    int[][] tabPoint = getHexagone(tailleHexagone);
                    Polygon hexa = new Polygon(tabPoint[0], tabPoint[1], 6); 
                    int positionX = (3*(j-1))*tailleHexagone+decalageX;
                    int positionY = (int) ((i*Math.sqrt(3)*tailleHexagone/2))+decalageY;
                    
                    hexa.translate(positionX, positionY);
                    g.setColor(Color.gray);
                    g.drawPolygon(hexa);
                    //ajout du polygon dans la liste des polygons de repérage
                    this.polygons.put(hexa, new Couple(i,j));
                    
                    //dessin de la case associé
                    Case laCase = this.carte.getCase(i, j);
                    g.setColor(laCase.getCouleur());
                    g.fillPolygon(hexa);
                    
                    //dessin des objets celestes
                    if(laCase.getImageObjetCeleste() != null) {
                        int TImage = (int)(tailleHexagone*laCase.getRTailleObjetCeleste());
                        g.drawImage(laCase.getImageObjetCeleste(), positionX-TImage/2, positionY-TImage/2,TImage,TImage, this);
                    }
                    
                    //dession des vaisseaux
                    if(laCase.getVaisseau() != null) {
                        int TImage = (int)(tailleHexagone);
                        g.drawImage(laCase.getVaisseau().getImage(), positionX-TImage/2, positionY-TImage/2,TImage,TImage, this);
                    }
                }
            }
        }
        
    }
    
    //fabrication d'un hexagone
    private int[][] getHexagone(int tailleHexagone) {
        int[] x = new int[6];
        int[] y = new int[6];
        double thetaInc = 2*Math.PI/6;
        double theta = thetaInc;
        for(int j = 0; j < 6; j++) {
            x[j] = (int)(tailleHexagone*Math.cos(theta));
            y[j] = (int)(tailleHexagone*Math.sin(theta));
            theta += thetaInc;
        }
        return new int[][]{ x, y };
    }
    
}
