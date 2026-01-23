import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Pubblicazione> pubblicazioni;

    public Biblioteca() {
        this.pubblicazioni = new ArrayList<>();
    }

    public void aggiungi(Pubblicazione pub) {
        pubblicazioni.add(pub);
        System.out.println("✅ Pubblicazione aggiunta: " + pub.getTitolo());
    }

    public ArrayList<Pubblicazione> getPubblicazioni() {
        return new ArrayList<>(pubblicazioni);  // Copia difensiva
    }

    public int size() {
        return pubblicazioni.size();
    }
}