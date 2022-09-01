package Domini;


import java.io.Serializable;

public class Pair<K, V> implements Serializable {

    public K first;
    public V second;

    //Pre: -
    //Post: Retorna un Pair<,> creat a partir de les variables passades com a argument
    //Descripció: Crea un pair a partir de dos elements
    public static <K, V> Pair<K, V> createPair(K first, V second) {
        return new Pair<K, V>(first, second);
    }

    //Pre: -
    //Post: Crea un Pair<,> creat a partir de les variables passades com a argument
    //Descripció: Crea un pair a partir de dos elements
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    //Pre: -
    //Post: Retorna el valor first d'un objecte Pair
    //Descripció: Retorna el primer valor d'un Pair
    public K getFirst() {
        return first;
    }

    //Pre: -
    //Post: Retorna el valor second d'un objecte Pair
    //Descripció: Retorna el second valor d'un Pair
    public V getSecond() {
        return second;
    }

}

