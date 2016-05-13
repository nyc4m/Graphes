package spaceconquest;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import spaceconquest.ObjetCeleste.Asteroide;
import spaceconquest.ObjetCeleste.Etoile;
import spaceconquest.Parties.Mode;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Raphaël
 */
public class JMenu extends javax.swing.JFrame {

    /**
     * Creates new form JMenu
     */
    public JMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                // Récupération de l'image , mise en place de l'image dans une nouvelle frame , mise à une taille spécial la frame ainsi que son apparation
                // Permet l'action de quitter la frame.
                String path = "Images/fondMenu.png";
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File(path));
                } catch (IOException ex) {
                    Logger.getLogger(JMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                Image contentPane = new Image(image);
                final JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(contentPane);
                frame.setSize(1366, 768);
                frame.setLocationRelativeTo(null);

                // création de nouveau button pour la frame et ajout des nouveaux button à celles-çi,   
                JButton bAuto = new JButton("MODE AUTOMATIQUE");
                JButton bManuel = new JButton("MODE MANUEL");
                frame.add(bManuel);
                frame.add(bAuto);

                // redimensionnage des buttons.
                bAuto.setPreferredSize(new Dimension(500, 100));
                bManuel.setPreferredSize(new Dimension(500, 100));

                // Permet de capter une action sur le button auto et d'éxécuter le Main SpaceConquest en mode Automatique.
                bAuto.addActionListener(new ActionListener() {
              private Partie partie;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Execute when button is pressed
                        SpaceConquest.main(args);
                            partie = new Partie(5);



        

                     SpaceConquest.main(args);
                        partie = new Partie(5);                    
                         
                                                
                        
                        partie.placerLicoShip(1, 2);
                        partie.placerLicoLand(15, 3);

                        partie.placerZombificator(1, 5);
                        partie.getCarte().addObjetCeleste(new Etoile(), 3, 2);    
                        partie.placerShadocksShip(8, 3);
                        partie.placerShadocksLand(10, 2);
                        

                     partie.setMode(Mode.automatique);  
                        partie.start();
                        
                        frame.dispose();
                    }
                });
                
                // Permet de capter une action sur le button manuel et d'éxécuter le Main SpaceConquest en mode Manuel.
                bManuel.addActionListener(new ActionListener() {
                    private Partie partie;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Execute when button is pressed
                       SpaceConquest.main(args);
                        partie = new Partie(5);                    
                         
                        partie.setMode(Mode.manuel);                            
                        
                        partie.placerLicoShip(1, 2);
                        partie.placerLicoLand(15, 3);

                        partie.placerZombificator(1, 5);
                        partie.getCarte().addObjetCeleste(new Etoile(), 3, 2);

                   
                        partie.start();
                        
                        frame.dispose();
                    }
                });
                frame.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
