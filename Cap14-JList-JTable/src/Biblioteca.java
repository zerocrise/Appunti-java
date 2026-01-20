import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Pubblicazione> pubblicazioni;

    public Biblioteca() {
        this.pubblicazioni = new ArrayList<>();
    }

    public void aggiungiLibro(String titolo, String autore, int anno,
                              String genere, int pagine, double prezzo)
            throws DatiNonValidiException {
        try {
            Libro libro = new Libro(titolo, autore, anno, genere, pagine, prezzo);
            pubblicazioni.add(libro);
            System.out.println("✅ Libro aggiunto con successo");

        } catch (IllegalArgumentException e) {
            throw new DatiNonValidiException("generale", e.getMessage());
        }
    }
    public void aggiungi(Libro l)
            throws DatiNonValidiException {
        try {

            pubblicazioni.add(l);
            System.out.println("✅ Libro aggiunto con successo");

        } catch (IllegalArgumentException e) {
            throw new DatiNonValidiException("generale", e.getMessage());
        }
    }

    public Pubblicazione cercaPerTitolo(String titolo)
            throws LibroNonTrovatoException {
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo di ricerca non valido");
        }

        for (Pubblicazione pub : pubblicazioni) {
            if (pub.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                return pub;
            }
        }

        throw new LibroNonTrovatoException(titolo);
    }

    public ArrayList<Pubblicazione> cercaPerAutore(String autore)
            throws LibroNonTrovatoException {
        ArrayList<Pubblicazione> risultati = new ArrayList<>();

        for (Pubblicazione pub : pubblicazioni) {
            if (pub instanceof Libro) {
                Libro libro = (Libro) pub;
                if (libro.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                    risultati.add(libro);
                }
            }
        }

        if (risultati.isEmpty()) {
            throw new LibroNonTrovatoException("autore: " + autore);
        }

        return risultati;
    }

    public void presta(int indice)
            throws IndexOutOfBoundsException, PubblicazioneNonDisponibileException {
        if (indice < 0 || indice >= pubblicazioni.size()) {
            throw new IndexOutOfBoundsException(
                    "Indice non valido: " + indice + " (0-" + (pubblicazioni.size()-1) + ")"
            );
        }

        Pubblicazione pub = pubblicazioni.get(indice);

        if (!pub.isDisponibile()) {
            throw new PubblicazioneNonDisponibileException(pub);
        }

        pub.presta();
    }

    public void restituisci(int indice) throws IndexOutOfBoundsException {
        if (indice < 0 || indice >= pubblicazioni.size()) {
            throw new IndexOutOfBoundsException("Indice non valido: " + indice);
        }

        pubblicazioni.get(indice).restituisci();
    }

    public void rimuovi(int indice) throws IndexOutOfBoundsException {
        if (indice < 0 || indice >= pubblicazioni.size()) {
            throw new IndexOutOfBoundsException("Indice non valido: " + indice);
        }

        Pubblicazione rimossa = pubblicazioni.remove(indice);
        System.out.println("✅ Rimossa: " + rimossa.getTitolo());
    }

    public ArrayList<Pubblicazione> getPubblicazioni() {
        return new ArrayList<>(pubblicazioni);  // Copia difensiva
    }

    public int size() {
        return pubblicazioni.size();
    }

    public boolean isEmpty() {
        return pubblicazioni.isEmpty();
    }
}
