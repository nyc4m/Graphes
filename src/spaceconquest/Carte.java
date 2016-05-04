/*
 * Gestion de la carte
 */
package spaceconquest;

import java.awt.Color;
import java.util.ArrayList;
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
    private ArrayList<Couple> asteroides;
    private Graphe grapheLicornes;
    private ArrayList<Integer> tabDistances;
    /**
     * Stocke les coordonnées du soleil pour en bannir l'accès
     */
    private Couple soleil;

//Constructeur
    public Carte(int _taille) {
        this.taille = _taille;
        this.cases = new HashMap<>();
        this.asteroides = new ArrayList();
        //initialisation de la map vide
        for (int i = 1; i <= 3 * _taille; i++) {
            for (int j = 1; j <= _taille; j++) {
                this.cases.put(new Couple(i, j), new Case());
            }
        }
        this.caseSelectionnee = null;
        this.setGrapheGrille();
        this.setGrapheZombie();
        this.setGrapheLicornes();
        this.tabDistances = new ArrayList();

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

            } else if (obj.getType().equals("asteroide")) {
                this.asteroides.add(c);
                this.setGrapheLicornes();
            }
            obj.setPosition(c);
            this.actualiser();
        }
    }

    /**
     * Permet d'actualiser la carte lors de l'ajout d'un objet
     */
    public void actualiser() {

        this.setGrapheZombie();
        this.setGrapheLicornes();
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
        
        //on stocke le numero du sommet pour le chercher dans le tableau
        int numCase = this.position(c.getX(), c.getY());
        //on regarde si la distance pour aller au sommet est au plus 2        
        boolean caseOk = this.tabDistances.get(numCase) <= 2;         
        
        if (c.equals(this.caseSelectionnee)) {
            //deselection de la case
            this.getCase(c).setCouleur(Couleur.Blanc);
            this.caseSelectionnee = null;
        } else //si une case avait déja été sélectionnée
            
        {
            
            if (this.caseSelectionnee != null && caseOk) {
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
            {
                if (this.getCase(c).getVaisseau() != null) {
                    if (this.getCase(c).getVaisseau().getRace() == SpaceConquest.getTour()) {
                        //on selectionne la case
                        this.getCase(c).setCouleur(Couleur.Rouge);
                        this.caseSelectionnee = c;
                    }
                }
            }
        }
    }

    /**
     * Methode definissant le graphe général de la carte Pour ce faire, on
     * utilise une double boucle et une variable rédéfinissant la position à
     * chaque tour de la deuxième boucle. Trois liaisons sont à réaliser :
     * position + n* 2; position + n; et suivant si n est pair ou impair,
     * position + n -1 ou position + n +1
     *
     *
     */
    public Graphe setGraphe(int taille) {
        Graphe graphe = new Graphe(taille);
        int nbSommet = graphe.getNbSommet();
        int n = this.taille;
        for (int i = 1; i <= nbSommet; i++) { //Parcours des lignes
            for (int j = 1; j <= n; j++) { //parcours des colonnes
                int position = j + (n * i) - n; //à chaque parcours de colonne, la position est redéfinie grâce à cette formule
                if (position + n * 2 <= nbSommet) { //On test que le sommet existe
                    graphe.ajouterArc(position, position + n * 2, 1);

                }
                if (position + n <= nbSommet) {
                    graphe.ajouterArc(position, position + n, 1);
                }

                if (i % 2 != 0) {                                               //On distingue deux cas, celui ou le numéro de la ligne est paire, et celui où il est impaire.
                    if ((position + n + 1 <= nbSommet) && (j < n)) {                         //On remarque que lorsque le numéro est paire, la position doit être reliée à :
                        graphe.ajouterArc(position, position + n + 1, 1);   //position+t-1 et position+t
                        //sinon elle doit être reliée à : 
                        //position+taille et position + taille +1
                    }
                } else if (position + n - 1 <= nbSommet && (j > 1 && j <= n)) {
                    graphe.ajouterArc(position, position + n - 1, 1);
                }
            }
        }
        return graphe;
    }

    /**
     * Méthode definissant le graphe de la carte (qui correspond au graphe
     * général
     */
    public void setGrapheGrille() {
        this.graphe = this.setGraphe(this.taille * this.taille * 3);
    }

    /**
     * Méthode retournant le graphe de la carte
     *
     * @return Retourne le graphe de la carte avec le type Graphe
     */
    public Graphe getGrapheGrille() {
        return this.graphe;
    }

    public Graphe isolerSommet(int s, Graphe g) {

        for (int i = 1; i <= this.taille * 3 * this.taille; i++) {
            g.modifierMatrice(s, i, 0);
            g.modifierMatrice(i, s, 0);
        }

        return g;

    }

    /**
     * Donne le numero du sommet en fonction des coordonnees
     *
     * @param x coordonnee x
     * @param y coordonnee y
     * @return numero du sommet
     */
    public int position(int x, int y) {
        return y + (x * this.taille) - this.taille;
    }

    /**
     * Methode permettant de définir le graphe des zombies. Ce graphe est
     * équivalant au graphe de la carte, à ceci prêt que la case contenant le
     * soleil n'est connectée à aucune autre (cela correspond à une ligne et une
     * colonne de zero aux coordonnées du soleil)
     */
    public void setGrapheZombie() {
        this.grapheZombie = this.graphe.clone();
        if (this.soleil != null) {
            int _soleil = this.position(this.soleil.getX(), this.soleil.getY());
            isolerSommet(_soleil, this.grapheZombie);

        }

    }

    /**
     * Permet de d'obtenir le graphe des zombies
     *
     * @return Retourne le graphe des zombies grâce au type Graphe
     */
    public Graphe getGrapheZombie() {
        return this.grapheZombie;
    }

    /**
     * Génère le graphe des licornes, dans lequel on ne peut pas accéder au
     * soleil, et aller sur un asteroide coute deux PA.
     */
    public void setGrapheLicornes() {
        this.grapheLicornes = this.grapheZombie.clone();
        if (this.asteroides != null) {
            for (int i = 0; i < this.asteroides.size(); i++) {
                //On boucle de manière à accéder à tous les asteroides.
                //pour chaque boucle, la position de l'astéroide est calculée
                int _asteroide = this.position(this.asteroides.get(i).getX(), this.asteroides.get(i).getY());
                //le graphe est parcouru ligne par ligne, et tous les 1 sont changés par des deux
                for (int j = 1; j <= this.taille * 3; j++) {
                    if (this.grapheLicornes.getMatrice(_asteroide, j) == 1) {
                        this.grapheLicornes.modifierMatrice(_asteroide, j, 2);
                    }
                }

            }
        }

    }

    public Graphe getGrapheLicornes() {
        return this.grapheLicornes;
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


    /**
     * Métohde permettant d'obtenir la position du vaisseau
     *
     * @param v Représente le vaisseau
     * @return rencoie un couple mdoélisant sa positon
     */
    public Couple getPosVaisseauCouple(Vaisseau v) {

        Couple c = new Couple(v.getPosition().getX(), v.getPosition().getY());

        return c;
    }
    
        public int getPosVaisseauInt(Vaisseau v) {

        int position = this.position(v.getPosition().getX(), v.getPosition().getY());

        return position ;
    }

    /**
     * Méthode permettant de renvoyer un couple à partir d'un sommet
     *
     * @param nbSommet Numéro du sommet actuelle
     * @param tailleGraphe Représente la taille du Graphe
     * @return
     */
    public Couple getCouple(int nbSommet, int tailleGraphe) {
        int x; //Représente le sommet actuelle
        int y;  //Représente la taille du graphe
        x = nbSommet / tailleGraphe;
        y = nbSommet % tailleGraphe;
        if (nbSommet % tailleGraphe != 0) { //cas ou nous somme à la fin d'une ligne
            x += 1;
        }
        if (y == 0) {  //cas ou nous somme à la fin d'une ligne
            y = this.taille;
        }
        Couple c = new Couple(x, y);
        return c;

    }

    /**
     * Méthode permettant de colorier les contrainte de déplacement
     *
     * @param g Graphe représentant les contraintes
     * @param v Représente le vaisseau
     */
    public void colorationMouvement(Graphe g, Couple v) {

        Dijkstra d = new Dijkstra(g);
        // on récupère la position du vaisseau passé  en paramètre
        int position = this.position(v.getX(), v.getY());
        //On lance un Dijkstra à partir de la position
        d.plusCourtChemin(position, this.position(this.soleil.getX(), this.soleil.getY()));
        this.tabDistances = d.getDistances();
        //parcour du tableau des distances
        for (int i = 1; i <= d.getDistances().size() - 1; i++) {
            // Si la diastance est de 1 on colorie en vert
            if (d.getDistances().get(i) == 1) {       // 1 point de déplacement
                Couple c = new Couple(getCouple(i, this.taille).getX(), getCouple(i, this.taille).getY());

                this.getCase(c).setCouleur(Couleur.Vert);
            }
            // Si la diastance est de 1 on colorie en jaune
            if (d.getDistances().get(i) == 2) // 2 points de déplacement
            {
                Couple c = new Couple(getCouple(i, this.taille).getX(), getCouple(i, this.taille).getY());

                this.getCase(c).setCouleur(Couleur.Jaune);

            }

        }

    }
}
