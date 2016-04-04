/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.TimerTask;
import java.util.Timer;
import spaceconquest.Parties.Mode;

/**
 *
 * @author simonetma
 */
public class TimerPartie extends Timer {

        private Partie partie;                                                  //partie en cours
        
            
    //constructuer    
    public TimerPartie(Partie partie) {
        super();
        this.partie = partie;
        if(this.partie.getMode() == Mode.automatique) {
            this.scheduleAtFixedRate(new TimerTaskPartie(this.partie), 0, 1000);
        }
    }
    
    //arret du timer si besoin
    public void stop() {
        this.cancel();
    }
    
    //sous classe privée
    private class TimerTaskPartie extends TimerTask {
        
        private Partie partie;
        
        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;
        }
        
        //fonction appellée à chaque tic du timer
        @Override
        public void run() {
            if(this.partie.isIHMReady()) {
                switch(this.partie.getTour()) {
                    case Licorne : this.tourDesLicornes(); break;
                    case Zombie : this.tourDesZombies(); break;
                }
                    this.partie.tourSuivant();
            }
        }
    
        //ce qu'il se passe lors du tour des zombies
        private void tourDesZombies() {
            System.out.println("Tour des Zombies !");
        }
            
        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {      
            System.out.println("Tour des Licornes !");
        }
    }    
}


