/*
 * Timer pour le mode automatique
 */
package spaceconquest;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import spaceconquest.Map.Couleur;
import spaceconquest.Map.Couple;
import spaceconquest.Parties.Mode;
import spaceconquest.Race.Vaisseau;

/**
 *
 * @author simonetma
 */
public class TimerPartie extends Timer {

    private Partie partie;        //partie en cours

    //constructuer    
    public TimerPartie(Partie partie) {
        super();
        this.partie = partie;
        if (this.partie.getMode() == Mode.automatique) {

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
        private ArrayList<Integer> cheminShadock;
        
        private Random randomGenerator;

        /**
         * contient le chemin le plus court pour atteindre les licornes
         */
        private ArrayList<Integer> cheminZombies;
        /**
         * contient le chemin le plus court pour aller sur la planete des
         * licornes
         */
        private ArrayList<Integer> cheminLicornes;
        /**
         * Le numero du tour des licornes
         */
        private int numEtapeLicorne;
        /**
         * Contient le Dijkstra des licornes
         */
        private Dijkstra licorne;
        /**
         * Contient le plus court chemin des zombies
         */
        private Dijkstra zombie;
        /**
         * Contient le plus court chemin des shadocks
         */
        private Dijkstra shadock;
        
        

        //constructeur
        public TimerTaskPartie(Partie partie) {
            this.partie = partie;

           
            
            shadock = new Dijkstra(partie.getCarte().getGrapheLicornes());
            cheminShadock = new ArrayList();
            cheminShadock = shadock.cheminShadock(partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY()), partie.getCarte().getSoleilInt());
            
            int posVaisseauLicornes = partie.getCarte().getPosVaisseauInt(partie.getLicoShip());
            int posVaisseauZombies = partie.getCarte().getPosVaisseauInt(partie.getZombificator());
            int posLicoLand = partie.getCarte().position(partie.getLicoLand().getPosition().getX(), partie.getLicoLand().getPosition().getY());

            numEtapeLicorne = 2;
            
             licorne = new Dijkstra(partie.getCarte().getGrapheLicornes());
             zombie = new Dijkstra(partie.getCarte().getGrapheZombie());
           
            licorne.plusCourtChemin(posVaisseauLicornes, partie.getCarte().getSoleilInt());
            
           

            this.cheminLicornes = new ArrayList();
            this.cheminZombies = new ArrayList();
            this.cheminLicornes = licorne.construireChemin(posVaisseauLicornes, posLicoLand);
            zombie.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getZombificator()), partie.getCarte().getSoleilInt());           

        }
        
        public int planeteProche(){
            Dijkstra d = new Dijkstra(this.partie.getCarte().getGrapheLicornes());
            d.plusCourtChemin(this.partie.getCarte().position(this.partie.getLicoShip().getPosition().getX(), this.partie.getLicoShip().getPosition().getY()), this.partie.getCarte().getSoleilInt());
            int distanceMin = d.infini(); //la distance la plus courte est 0 pour commencer;
            int planete = -1;
            int _case = 0;
            for(Couple e : this.partie.getCarte().getLicoLands()){
                _case = this.partie.getCarte().position(e.getX(), e.getY());
                if(d.getDistances().get(_case) < distanceMin){
                    distanceMin = d.getDistances().get(_case);
                    planete = _case;
                }
            }
            
            return _case;
        }

        //fonction appellée à chaque tic du timer
        @Override
        public void run() {
            if (this.partie.isIHMReady()) {
                switch (this.partie.getTour()) {
                    case Licorne:
                        this.tourDesLicornes();
                        break;
                    case Zombie:
                        this.tourDesZombies();
                        break;
                    case Shadocks:
                        this.tourDesShadocks();
                        break;
                }
                this.partie.tourSuivant();
            }
        }

        //ce qu'il se passe lors du tour des shadocks
        private void tourDesShadocks() {
            System.out.println("Tour des Shadocks !");
            
            

            if (this.partie.getModeAuto() == true) {
                 
                boolean fini = false;
                Couple caseActuelle = partie.getShadocks().getPosition();
                int posShadockPlanete = partie.getCarte().position(partie.getShadocksLand().getPosition().getX(), partie.getShadocksLand().getPosition().getY());
                int posShadockVaisseau = partie.getCarte().getPosVaisseauInt(partie.getShadocks());
                int posLicoland = partie.getCarte().position(partie.getLicoLand().getPosition().getX(), partie.getLicoLand().getPosition().getY());
                partie.getCarte().getCase(caseActuelle).setCouleur(Couleur.Rouge);

                while (fini != true) {
                    randomGenerator = new Random();
                    int index = randomGenerator.nextInt(cheminShadock.size());
                    int sommet = cheminShadock.get(index);
                    
                    Dijkstra d = new Dijkstra(partie.getCarte().getGrapheZombie());
                    d.plusCourtChemin(partie.getCarte().getPosVaisseauInt(partie.getShadocks()), partie.getCarte().getSoleilInt());
                    
                    
                    if (d.getDistances().get(sommet) <= 1 && sommet != posShadockPlanete && sommet != posShadockVaisseau && sommet != posLicoland) {
                  
                        Couple prochaineCase = partie.getCarte().getCouple(sommet, partie.getCarte().getTaille());
                        partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
                        partie.getShadocks().setPosition(prochaineCase);
                        fini = true;
                    
                    }
                }

            }
        }
        

        //ce qu'il se passe lors du tour des zombies
        private void tourDesZombies() {
            System.out.println("Tour des Zombies !");
            if (this.partie.getModeAuto() == true) {
                int licornes = this.partie.getCarte().getPosVaisseauInt(this.partie.getLicoShip());
                this.deplacement(this.partie.getCarte().getGrapheZombie(), this.partie.getZombificator(), licornes);
               
              


            }
        }
        
        public void marquerCouleur(Couple c){
            switch(this.partie.getTour()){
                case Licorne : 
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Vert);
                    break;
                case Zombie : 
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Jaune);
                    break;
                case Shadocks:
                    this.partie.getCarte().getCase(c).setCouleur(Couleur.Rouge);
            }
        }
        
        private void deplacerVaisseau(Couple prochaineCase) {
            switch(this.partie.getTour()){
                case Licorne : 
                    this.partie.getLicoShip().setPosition(prochaineCase);
                    break;
                case Zombie :
                    this.partie.getZombificator().setPosition(prochaineCase);
                    break;
                case Shadocks:
                    this.partie.getShadocks().setPosition(prochaineCase);
            }
        }
        
        public void deplacement(Graphe graphe, Vaisseau v, int cible){
            Dijkstra chemin = new Dijkstra(graphe);
            chemin.plusCourtChemin(this.partie.getCarte().getPosVaisseauInt(v), this.partie.getCarte().getSoleilInt());
            ArrayList<Integer> pcChemin = chemin.construireChemin(this.partie.getCarte().getPosVaisseauInt(v), cible);
            
            Couple caseActuelle = partie.getCarte().getCouple(pcChemin.get(0), this.partie.getCarte().getTaille());
            Couple prochaineCase = partie.getCarte().getCouple(pcChemin.get(1), this.partie.getCarte().getTaille());
            
            this.marquerCouleur(caseActuelle);
            
            this.partie.getCarte().BougerVaisseau(caseActuelle, prochaineCase);
            
            this.deplacerVaisseau(prochaineCase);
            
            
        }

        //ce qu'il se passe lors du tour des licornes
        private void tourDesLicornes() {
            System.out.println("Tour des Licornes !");

            if (this.partie.getModeAuto() == true) {
                int planete_la_plus_proche = this.planeteProche();
                this.deplacement(this.partie.getCarte().getGrapheLicornes(), this.partie.getLicoShip(), planete_la_plus_proche);

            }

            partie.refreshCarte();
        }

        

    }
}