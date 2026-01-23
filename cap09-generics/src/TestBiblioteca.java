
// Programma principale
public class TestBiblioteca {
    public static void main(String[] args) {
        // Biblioteca solo per libri
        Biblioteca<Libro> bibliotecaLibri = new Biblioteca<>("Biblioteca Comunale");

        Scaffale<Libro> scaffaleNarrativa = new Scaffale<>("Narrativa", 3);
        Scaffale<Libro> scaffaleFantascienza = new Scaffale<>("Fantascienza", 5);

        bibliotecaLibri.aggiungiScaffale(scaffaleNarrativa);
        bibliotecaLibri.aggiungiScaffale(scaffaleFantascienza);

        // Aggiungiamo libri
        scaffaleNarrativa.aggiungi(new Libro("1984", "George Orwell", 1949));
        scaffaleNarrativa.aggiungi(new Libro("Il Nome della Rosa", "Umberto Eco", 1980));

        scaffaleFantascienza.aggiungi(new Libro("Neuromante", "William Gibson", 1984));
        scaffaleFantascienza.aggiungi(new Libro("Fondazione", "Isaac Asimov", 1951));

        bibliotecaLibri.mostraTutto();

        // Ricerca globale
        System.out.println("\n--- Ricerca 'fondazione' ---");
        ArrayList<Libro> risultati = bibliotecaLibri.cercaGlobale("fondazione");
        for (Libro libro : risultati) {
            System.out.println("Trovato: " + libro.getInfo());
        }

        // Biblioteca per riviste
        Biblioteca<Rivista> bibliotecaRiviste = new Biblioteca<>("Emeroteca");
        Scaffale<Rivista> scaffaleScienza = new Scaffale<>("Riviste Scientifiche", 10);
        bibliotecaRiviste.aggiungiScaffale(scaffaleScienza);

        scaffaleScienza.aggiungi(new Rivista("Nature", 7923, 2024));
        scaffaleScienza.aggiungi(new Rivista("Science", 425, 2024));

        bibliotecaRiviste.mostraTutto();
    }
}

