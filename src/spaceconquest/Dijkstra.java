/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceconquest;
import java.util.ArrayList;

/**
 *
 * @author bp818669
 */
public class Dijkstra  {
    /**
     * Le tableau contenant la liste des sommets. Ce tableau sert principalement aux tests de l'algorithme, il n'a pas de réelle utilité pour l'execution.
     */
    private ArrayList<Integer> sommets;
    /**
     * Tableau permettant de savoir quels sommets ont déjà été traités
     */
    private ArrayList<Boolean> mark;
    /**
     * Tableau contenant les distances pour chacun des sommets
     */
    private ArrayList<Integer> distances;
    /**
     * Tableaux cointenant la liste des antécédents de chaque sommet
     */
    private ArrayList<Integer> antecedents;
    /**
     * Contient un clone du graphe à traiter
     */
    private Graphe graphe;
    
    /**
     * Constructeur de dijkstra permet principalement d'initialiser toutes les listes, et de cloner le graphe concerné
     * @param g 
     */
    public Dijkstra(Graphe g){
        this.sommets = new ArrayList();
        this.mark = new ArrayList();
        this.distances = new ArrayList();
        this.antecedents = new ArrayList();
        this.graphe = g.clone();
    }
    
       
    /**
     * Cette méthode sert à "représenter" la valeur +OO. Elle correspond à l'addition de l'ensemble des poids des arêtes +1. 
     * Il est impossible de trouver une distance plus longue
     * @return La somme de tous les poids des arêtes+1
     */
    public int infini(){
        int res = 0;
        for(int i = 1; i <= this.graphe.getNbSommet(); i++){
            for(int j = 1; j <= this.graphe.getNbSommet(); j++){
            res+= this.graphe.getMatrice(i, j);
            }
        }
        
        return res+1;
        
    }
    /**
     * Méthode permettant d'intialiser tous les tableaux concernés par l'algorithme
     */
    public void intitialisation(int dep, int soleil) {
     /**
      * Cette partie sert à attribuer une valeur -qui ne gênera pas l'algorithme- à l'indice 0 de tous les tableaux.
      * Ainsi, lorsqu'une valeur sera entrée dans un tableau, elle sera entrée à l'indice correspondant au sommet à laquelle elle est associée.
      * exemple : la valeur entrée à l'indice 3 du tableau distances correspond à la distance minimum pour aller au sommet 3.
      */
     //////////////////////////////////
        
            this.sommets.add(this.infini());
            this.antecedents.add(this.infini());
            this.mark.add(Boolean.TRUE);
            this.distances.add(this.infini());
    
        ///////////////////////////////
        
        
        //On parcourt l'ensemble des tableaux à partir de l'indice 1, jusqu'à la fin du tableau. ce qui correspond à attribuer une valeur pour chacun des sommets.
        for(int i=1;i<=graphe.getNbSommet();i++) {
            
            this.sommets.add(i);
            this.mark.add(Boolean.FALSE);
            this.distances.add(this.infini());
            this.antecedents.add(-1); //Chaque sommet de voit attribuer -1 pour antécédent. C'est une valeur arbitraire.  
        }
        
        //Le premier sommet se voit attribuer une distance de 0
        this.distances.set(dep, 0);
        //Le soleil est marque comme traite, ce aui evite un calcul impossible pour l'algo
        this.mark.set(soleil, Boolean.TRUE);
        
    }
    
    
    /**
     * Méthode permettant d'afficher de manière brute l'état de tous les tableau. 
     * C'est l'unique fois ou le tableau sommet est utilisé, pour associer chaque valeur à un sommet.
     */
    public void afficheTableaux(){
        for(int e : this.sommets){
            System.out.print(e + " ");
        }
        System.out.println("\n");
        for(boolean e : this.mark){
            System.out.print(e + " ");
        }
        System.out.println("\n");
        for(int e : this.distances){
            System.out.print(e + " ");
        }
        System.out.println("\n");
        for(int e : this.antecedents){
            System.out.print(e + " ");
        }
        
        
    }
    /**
     * Méthode permettant de savoir si tous les sommets ont été traité. pour ce faire, tous le tableau mark est parcouru.
     * Si le tableau contient un "false" c'est que tous les sommets n'ont pas été traité.
     * @return Retourne faux si tous les sommets n'ont pas été traité, et vrai si tous les sommets ont été traités.
     */
    public boolean listeTerminee(){
        boolean res = true;
        for(boolean e : this.mark){
            if(e == false){
                res = false;
            }
        }
        
        return res;
    }
    /**
     * Méthode l'indice de la distance minimum du tableau distance, parmis les sommets non traités.
     * @return renvoi l'indice du tableau distances contenant la distance la plus courte.
     */
    public int minDistance(){
        int valMin = this.distances.get(0); //valMin prend la valeur contenu à l'indice 0 du tableau distance (la valeur est donc +OO)
        int rang = 0; //le rang est intialisé à 0 ce qui correspond à l'indice de la valeur ci dessus.
        for(int i = 1; i<this.distances.size(); i++){ //tous le tableau des distances est parcouru pour trouver la distance minimum.
            /**
             * A chaque itération, on effectue un test :
             * si la valeur contenu à l'indice i du tableau est inférieur à valMin ET QUE LE SOMMET ASSOCIE N'A JAMAIS ETE TRAITE
             * alors on remplace la valeur minimum dans valMin
             * et on stocke son indice associé.
             */
            if(valMin>this.distances.get(i) && this.mark.get(i) == false){
                valMin = this.distances.get(i);
                rang = i;
            }
        }
        
        //on retourne le rang de la valeur minimum à la fin de la méthode.
        return rang;
    }
    
    /**
     * Méthode permettant de connaitre l'indice de la valeur minimum du tableau distance.
     * Elle servait pour l'ancienne version de minDistance.
     * @return le rang de la distance minimum.
     * @deprecated
     */
    @Deprecated
    public int rangSommetMin(){
        return this.distances.indexOf(this.minDistance());
    }
    
    /**
     * Méthode permettant de mettre à jour le tableau des distances et des antécédents chaque fois qu'un chemin plus court est trouvé.
     * @param a Le sommet de départ/a traiter
     * @param b Le sommet d'arrivé
     */
    public void maj_distance(int a, int b){
        
        /**
         * Les distances ont été stockées pour plus de lisibilité.
         */
        
        ////////////////////////////////////////////////
        int distanceAB = this.graphe.getMatrice(a, b);
        int distanceA = this.distances.get(a);
        int distanceB = this.distances.get(b);
        int nvelleDistanceAB = distanceA+distanceAB;
        ////////////////////////////////////////////////
        
        /**
         * Si la distance pour aller au sommet B est plus longue que la distance de A jusqu'à B
         * ET
         * Que la distance AB n'est pas de 0 (qui correspondrait à deux sommets A et B qui ne sont pas liés)
         * ET
         * Que le sommet B n'a pas déjà été traité.
         * 
         * ALORS
         * on met à jour la distance pour aller à B en ajoutant la distance de A. 
         *
         */
        if(distanceB > nvelleDistanceAB && distanceAB != 0 && this.mark.get(b) != true){
            
            this.distances.set(b, nvelleDistanceAB);
            this.antecedents.set(b, a);
        }
        
    }
    
    /**
     * Methode principale permettant de calculer le plus court chemin.
     */
    public void plusCourtChemin(int dep, int soleil){
        this.intitialisation(dep, soleil);
        while(!this.listeTerminee()){
            int x = this.minDistance();
            this.mark.set(x, Boolean.TRUE);
            for(int i = 1; i <= this.graphe.getNbSommet(); i++){
                this.maj_distance(x, i);
            }
        }
    }

    
    //////////////////////ACCESSEURS//////////////////////////////
    public ArrayList<Integer> getSommets() {
        return sommets;
    }

    public ArrayList<Boolean> getMark() {
        return mark;
    }

    public ArrayList<Integer> getDistances() {
        return distances;
    }

    public ArrayList<Integer> getAntecedents() {
        return antecedents;
    }

    public Graphe getGraphe() {
        return graphe;
    }
    
    
    
}
