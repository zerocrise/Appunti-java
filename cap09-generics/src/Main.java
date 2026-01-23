public class Main {
    public static void main(String[] args) {

// Istanzio uno scaffale per i Libri
                Scaffale<Libro> scaffaleLibri = new Scaffale<>(10);
                scaffaleLibri.aggiungi(new Libro("1984", "Orwell"));
                scaffaleLibri.aggiungi(new Libro("Fondazione", "Asimov"));
                scaffaleLibri.aggiungi(new Rivista("Focus", 245)); // ERRORE di compilazione!

                Libro l = scaffaleLibri.prendi(0); // Nessun cast!

                Scaffale<Rivista> scaffaleRiviste = new Scaffale<>(5);
                scaffaleRiviste.aggiungi(new Rivista("Focus", 245));
            }
        }






