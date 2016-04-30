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
public class Dijkstra {
    
    private ArrayList<Integer> sommets;
    private ArrayList<Boolean> mark;
    private ArrayList<Integer> distances;
    private ArrayList<Integer> antecedents;
    private Graphe graphe;
    
    public Dijkstra(Graphe g){
        this.sommets = new ArrayList();
        this.mark = new ArrayList();
        this.distances = new ArrayList();
        this.antecedents = new ArrayList();
        this.graphe = g;
    }
    
    public int infini(){
        int res = 0;
        for(int i = 1; i <= this.graphe.getNbSommet(); i++){
            for(int j = 1; j <= this.graphe.getNbSommet(); j++){
            res+= this.graphe.getMatrice(i, j);
            }
        }
        
        return res+1;
        
    }
    
    public void intitialisation() {
        this.sommets.add(this.infini());
        this.antecedents.add(this.infini());
        this.mark.add(Boolean.TRUE);
        this.distances.add(this.infini());
        
        for(int i=1;i<=graphe.getNbSommet();i++) {
            
            this.sommets.add(i);
            this.mark.add(Boolean.FALSE);
            this.distances.add(this.infini());
            this.antecedents.add(-1);   
        }
        
        this.distances.set(1, 0);
        //this.mark.set(1, );
    }
    
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
    
    public boolean listeTerminee(){
        boolean res = true;
        for(boolean e : this.mark){
            if(e == false){
                res = false;
            }
        }
        
        return res;
    }
    
    public int minDistance(){
        int valMin = this.distances.get(0);
        //cette boucle doit etre changee en boucle for normale
        //(me demander pour expliquation)
        for(int e  : this.distances){
            if(valMin>e && this.mark.get(this.distances.indexOf(e)) == false){
                valMin = e;
            }
        }
        
        return valMin;
    }
    
    public int rangSommetMin(){
        return this.distances.indexOf(this.minDistance());
    }
    
    public void maj_distance(int a, int b){
        int distanceAB = this.graphe.getMatrice(a, b);
        int distanceA = this.distances.get(a);
        int distanceB = this.distances.get(b);
        int nvelleDistanceAB = distanceA+distanceAB;
        if(distanceB > nvelleDistanceAB && distanceAB != 0 && this.mark.get(b) != true){
            
            this.distances.set(b, nvelleDistanceAB);
            this.antecedents.set(b, a);
        }
    }
    
    public void plusCourtChemin(){
        this.intitialisation();
        while(!this.listeTerminee()){
            int x = this.rangSommetMin();
            this.mark.set(x, Boolean.TRUE);
            for(int i = 1; i <= this.graphe.getNbSommet(); i++){
                this.maj_distance(x, i);
            }
        }
    }
    
}
