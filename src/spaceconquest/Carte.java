/*
 * Gestion de la carte
 */
package spaceconquest;

import java.awt.Color;
import java.util.HashMap;
import spaceconquest.Map.Case;
import spaceconquest.Map.Couleur;
import spaceconquest.Map.Couple;
import spaceconquest.ObjetCeleste.ObjetCeleste;
import spaceconquest.Race.Vaisseau;

/**
 *
 * @author simonetma
 */
public class Carte {

    private int taille;                                                             //nombre de "colonne" de la map, (la map a 3 fois plus de lignes que de colonnes)
    private HashMap<Couple, Case> cases;                                             //listes des cases
    private Couple caseSelectionnee;                                                //case actuellement sélectionnée par le joueur
    private Graphe graphe;
    private Graphe grapheZombie;
    /**
     * Stocke les coordonnées du soleil pour en bannir l'accès
     */
    private Couple soleil;

//Constructeur
    public Carte(int _taille) {
        this.taille = _taille;
        this.cases = new HashMap<>();
        //initialisation de la map vide
        for (int i = 1; i <= 3 * _taille; i++) {
            for (int j = 1; j <= _taille; j++) {
                this.cases.put(new Couple(i, j), new Case());
            }
        }
        this.caseSelectionnee = null;
        this.setGrapheGrille();
        this.grapheZombie = this.graphe;
    }

//getteur de la taille de la map
    public int getTaille() {
        return this.taille;
    }

//getteur de la case en position i,j
    public Case getCase(int i, int j) {
        return this.cases.get(new Couple(i, j));
    }

//getteur de la case en position c (couple)
    public Case getCase(Couple c) {
        return this.cases.get(c);
    }

//ajoute un objet celeste (étoile, astéroide...) à la position i,j (Passer par la classe partie !)
    public void addObjetCeleste(ObjetCeleste obj, int i, int j) {
        this.getCase(i, j).addObjetCeleste(obj);
        if (obj != null) {
            Couple c = new Couple(i, j);
            if (obj.getType().equals("etoile")) {
                soleil = c;
            }
            obj.setPosition(c);
        }
    }

//ajoute un vaisseau à la position i,j (Passer par la classe partie !)
    public void addVaisseau(Vaisseau v, int i, int j) {
        this.getCase(i, j).addVaisseau(v);
        if (v != null) {
            v.setPosition(new Couple(i, j));
        }
    }

//fait bouger le vaisseau présent en case départ à la case arrivée (détruisant tout vaisseau présent à cette case)
    public void BougerVaisseau(Couple depart, Couple arrivee) {
        if (this.getCase(depart).getVaisseau() == null) {
            System.err.println("ERREUR : Aucun vaisseau en case " + depart);
            System.exit(0);
        }
        if (this.getCase(arrivee).getVaisseau() != null) {
            System.out.println("Le " + this.getCase(arrivee).getVaisseau() + " a été détruit !");
            this.getCase(arrivee).getVaisseau().setPosition(null);
        }
        this.getCase(arrivee).addVaisseau(this.getCase(depart).getVaisseau());
        this.getCase(depart).addVaisseau(null);
    }

//méthode gérant ce qu'il se passe quand on clique sur une case en mode manuel
    public void selectionCase(Couple c) {
        if (c.equals(this.caseSelectionnee)) {
            //deselection de la case
            this.getCase(c).setCouleur(Couleur.Blanc);
            this.caseSelectionnee = null;
        } else //si une case avait déja été sélectionnée
         if (this.caseSelectionnee != null) {
                //ajouter des conditions de déplacement
                //on fait bouger le vaisseau
                this.BougerVaisseau(this.caseSelectionnee, c);
                //on déselectionne la case
                this.getCase(this.caseSelectionnee).setCouleur(Couleur.Blanc);
                this.caseSelectionnee = null;
                //on passe le tour
                SpaceConquest.tourSuivant();
            } else //si aucune case n'avait été selectionné
            //on vérifie que la case nouvellement sélectionné contient un vaisseau du joueur en cours
             if (this.getCase(c).getVaisseau() != null) {
                    if (this.getCase(c).getVaisseau().getRace() == SpaceConquest.getTour()) {
                        //on selectionne la case
                        this.getCase(c).setCouleur(Couleur.Rouge);
                        this.caseSelectionnee = c;
                    }
                }
    }

    /**
     * Methode definissant le graphe de la carte
     *
     *
     */
    public void setGrapheGrille() {
        this.graphe = new Graphe(this.taille * 3 * this.taille);
        int nbSommet = this.graphe.getNbSommet();
        int n = this.taille;
        for (int i = 1; i <= nbSommet; i++) { //Parcours des lignes
            for (int j = 1; j <= n; j++) { //parcours des colonnes
                int position = j + (n * i) - n; //à chaque parcours de colonne, la position est redéfinie grâce à cette formule
                if (position + n * 2 <= nbSommet) { //On test que le sommet existe
                    this.graphe.ajouterArc(position, position + n * 2, 1);

                }
                if (position + n <= nbSommet) {
                    this.graphe.ajouterArc(position, position + n, 1);
                }

                if (i % 2 != 0) {                                               //On distingue deux cas, celui ou le numéro de la ligne est paire, et celui où il est impaire.
                    if ((position + n + 1 <= nbSommet) && (j<n)) {                         //On remarque que lorsque le numéro est paire, la position doit être reliée à :
                        this.graphe.ajouterArc(position, position + n + 1, 1);   //position+t-1 et position+t
                                                                                //sinon elle doit être reliée à : 
                                                                                //position+taille et position + taille +1
                    }

                    if ((position + n - 1 <= nbSommet) && (j > n)) {
                        this.graphe.ajouterArc(position, position + n - 1, 1);
                    }
                }
            }

        }
    }

    

    

    public Graphe getGrapheGrille() {
        return this.graphe;
    }

    /**
     * Permet d'obtenir la position du soleil
     *
     * @return Retourne un couple contenant les coordonnées du soleil
     */
    public Couple getSoleil() {
        return soleil;
    }

    /**
     * Permet de colorer tout la carte en blanc
     */
    public void effacerColoration() {

        for (int i = 1; i <= 3 * this.taille; i++) { //parcour des cases
            for (int j = 1; j <= this.taille; j++) {

                Couple c = new Couple(i, j); //représente la case

                this.getCase(c).setCouleur(Couleur.Blanc); //On colorie la case en blanc

            }
        }
    }
}
