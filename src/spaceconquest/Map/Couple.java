/*
 * Couple d'entier pour gestion de coordonnées
 */
package spaceconquest.Map;

/**
 *
 * @author simonetma
 */
public class Couple {
    private int x;
    private int y;
    
    //constructeur
    public Couple(int _x,int _y) {
        this.x = _x;
        this.y = _y;
    }
    
    //getteurs
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    
    //gestion de l'egalité
    @Override
    public boolean equals(Object o) {
        if (o instanceof Couple) {
            Couple c = (Couple) o;
            return (this.x == c.x) && (this.y == c.y);
        }
        return false;
    }

    //hachage pour teste d'égalité
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }
    
    //affichage
    public String toString() {
        return this.x+"/"+this.y;
    }
}
