package Domini;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Perfil implements Serializable {
    private String username;
    private String password;
    private Map<String, Integer> records;

    private static final long serialVersionUID = 6529685098267757690L;

    //Pre: -
    //Post: Es crea una instància de Perfil amb el nom i contrasenya passats com a paràmetre
    //Descripció: Creadora de la classe Perfil
    public Perfil(String nom, String contraseña) {
        this.username=nom;
        this.password=contraseña;
        records = new HashMap<String, Integer>();
    }

    //Pre: -
    //Post: Actualitza el temps del taulell nomTaulell si es inferior al temps guardat o si no existia ningun temps per el taulell. No fa res en cas contrari
    //Descripció: Actualitza el record per un taulell, si cal
    public void actualitzarRecords(String nomTaulell, int temps) {
        if (!records.containsKey(nomTaulell) || records.get(nomTaulell)>temps) records.put(nomTaulell, temps);
    }

    //Pre: username no es null
    //Post: retorna username
    //descripcio: getter de username
    public String getUsername() {
        return username;
    }

    //Pre: password no es null
    //Post: retorna password
    //descripcio: getter de password
    public String getPassword() {
        return password;
    }

    //Pre: records no es null
    //Post: retorna el Integer (temps) del record indicat per la key name si hi havia un temps per a aquesta key.
        //sino, retorna -2
    //descripcio: getter d'un record concret
    public Integer getRecord(String name) {
        Integer result = records.get(name);
        if(result == null)
            return -2;
        return result;
    }
}