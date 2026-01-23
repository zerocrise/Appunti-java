import java.util.ArrayList;
import java.util.Comparator;

// Programma di test
public class TestBibliotecaAvanzata {
    public static void main(String[] args) {
        BibliotecaAvanzata biblioteca = new BibliotecaAvanzata();

        // Popolamento
        biblioteca.aggiungiLibro(new Libro("978-0451524935", "1984",
                "George Orwell", 1949, 328));
        biblioteca.aggiungiLibro(new Libro("978-0451526342", "La Fattoria degli Animali",
                "George Orwell", 1945, 112));
        biblioteca.aggiungiLibro(new Libro("978-0441569595", "Neuromante",
                "William Gibson", 1984, 271));
        biblioteca.aggiungiLibro(new Libro("978-0553293357", "Fondazione",
                "Isaac Asimov", 1951, 255));
        biblioteca.aggiungiLibro(new Libro("978-0553293364", "Io, Robot",
                "Isaac Asimov", 1950, 224));
        biblioteca.aggiungiLibro(new Libro("978-1451673319", "Fahrenheit 451",
                "Ray Bradbury", 1953, 249));

        // Statistiche
        biblioteca.mostraStatistiche();

        // Ricerche
        System.out.println("\n--- Ricerca per ISBN ---");
        Libro trovato = biblioteca.cercaPerISBN("978-0451524935");
        if (trovato != null) {
            System.out.println("Trovato: " + trovato.getInfoCompleta());
        }

        System.out.println("\n--- Libri di Orwell ---");
        ArrayList<Libro> libriOrwell = biblioteca.cercaPerAutore("George Orwell");
        for (Libro l : libriOrwell) {
            System.out.println("- " + l.getTitolo() + " (" +
                    l.getAnnoPubblicazione() + ")");
        }

        System.out.println("\n--- Libri anni '50 (1950-1959) ---");
        ArrayList<Libro> anni50 = biblioteca.cercaPerIntervalloAnni(1950, 1959);
        for (Libro l : anni50) {
            System.out.println("- " + l.getTitolo() + " di " +
                    l.getAutore() + " (" + l.getAnnoPubblicazione() + ")");
        }

        // Catalogo ordinato per autore
        System.out.println("\n--- Catalogo per Autore ---");
        biblioteca.mostraCatalogo(Comparator.comparing(Libro::getAutore)
                .thenComparing(Libro::getTitolo));
    }
}
