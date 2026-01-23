package model;

import exceptions.DatiNonValidiException;
import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Pubblicazione> pubblicazioni;

    public Biblioteca() {
        this.pubblicazioni = new ArrayList<>();
    }

    public void aggiungi(Pubblicazione pub) {
        pubblicazioni.add(pub);
    }

    public void rimuovi(int index) {
        if (index >= 0 && index < pubblicazioni.size()) {
            pubblicazioni.remove(index);
        }
    }

    public ArrayList<Pubblicazione> getPubblicazioni() {
        return new ArrayList<>(pubblicazioni);
    }

    public int size() {
        return pubblicazioni.size();
    }

    public boolean isEmpty() {
        return pubblicazioni.isEmpty();
    }
}