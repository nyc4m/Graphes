/*
 * Classe de gestion des graphes
 */
package spaceconquest;

import java.util.HashMap;
import spaceconquest.Map.Couple;

/**
 *
 * @author simonetma
 */
public class Graphe {

    private int nbSommet;
    private HashMap<Couple, Integer> matrice;
    private Boolean oriente;

    //constructeur
    public Graphe(int n) {
        this.nbSommet = n;
        this.matrice = new HashMap<>();
        this.oriente = false;
    }

    //renvoie le nombre de sommet du graphe
    public int getNbSommet() {
        return this.nbSommet;
    }

    //*************** gestion de la matrice d'adjacence ***********************
    //Modifie la valeur (i,j) de la matrice d'adjacence du graphe
    public void modifierMatrice(int i, int j, int valeur) {
        if (i <= 0 || j <= 0) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient (" + i + "," + j + ") !");
        } else if (i > this.nbSommet || j > this.nbSommet) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient (" + i + "," + j + ") !");
        } else {
            Couple c = new Couple(i, j);
            this.matrice.put(c, valeur);
        }
    }

    public void ajouterArc(int i, int j, int valeur) {
        modifierMatrice(i, j, valeur);
        if (!this.oriente) {
            modifierMatrice(j, i, valeur);
        }
    }

    //renvoie la valeur du coefficient (i,j) de la matrice d'adjacence (0 par défaut)
    public int getMatrice(int i, int j) {
        if (i <= 0 || j <= 0) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient (" + i + "," + j + ") !");
        } else if (i > this.nbSommet || j > this.nbSommet) {
            System.err.println("Erreur ! La matrice d'adjacence ne possède pas de coefficient (" + i + "," + j + ") !");
        } else {
            Couple c = new Couple(i, j);
            if (this.matrice.containsKey(c)) {
                return this.matrice.get(c);
            }
        }
        return 0;
    }
    /**
     * Clone le graphe courant
     * @return retourne un Graphe contenant une copie du graphe courant
     */
    public Graphe clone(){
        Graphe clone = new Graphe(this.nbSommet); //Un nouveau graphe est créé contenant le même nombre de sommet.
        for(int i = 1; i <= this.nbSommet; i++){ //On parcourt toutes les lignes
            for(int j = 1; j <= this.nbSommet; j++){ //et toutes les colonnes
                clone.ajouterArc(i, j, this.getMatrice(i, j)); //Pour chaque elements de la matrice de clone, on attribue la valeur au même coordonnées de la matrice du graphe courant/
            }
        }
        
        return clone;
        
    }

    //renvoie l'orientation
    public boolean getOrientation() {
        return this.oriente;
    }

    //affiche la matrice d'adjaceance
    @Override
    public String toString() {
        String ret = "<html><center>Matrice du graphe :<br><br>";
        for (int i = 1; i <= this.nbSommet; i++) {
            for (int j = 1; j <= this.nbSommet; j++) {
                Couple c = new Couple(i, j);
                if (this.matrice.containsKey(c)) {
                    ret += this.matrice.get(c);
                } else {
                    ret += "0";
                }
                if (j < this.nbSommet) {
                    ret += " ";
                }
            }
            if (i < this.nbSommet) {
                ret += "<br>";
            }
        }
        ret += "</center></html>";
        return ret;
    }

    //
}
