import java.time.LocalDate;
import java.util.Map;

// Programma di test completo
public class TestGestionePrestiti {
    public static void main(String[] args) {
        // Crea libri
        Libro libro1 = new Libro("978-0451524935", "1984", "George Orwell", 1949, 328);
        Libro libro2 = new Libro("978-0441569595", "Neuromante", "William Gibson", 1984, 271);
        Libro libro3 = new Libro("978-0553293357", "Fondazione", "Isaac Asimov", 1951, 255);

        GestionePrestiti gestione = new GestionePrestiti();

        // Crea prestiti
        System.out.println("=== CREAZIONE PRESTITI ===\n");
        gestione.creaPrestito(libro1, "Mario Rossi");
        gestione.creaPrestito(libro2, "Anna Bianchi");
        gestione.creaPrestito(libro3, "Luigi Verdi");

        // Tenta di prestare libro già in prestito
        System.out.println("\n--- Tentativo di prestito duplicato ---");
        gestione.creaPrestito(libro1, "Carlo Neri");

        // Mostra prestiti attivi
        gestione.mostraPrestitiAttivi();

        // Restituisci un libro
        System.out.println("\n=== RESTITUZIONE ===\n");
        gestione.restituisci(libro2);

        // Test con prestito scaduto (simulato)
        System.out.println("\n=== SIMULAZIONE PRESTITO SCADUTO ===\n");
        LocalDate dataPassata = LocalDate.now().minusDays(35);
        Prestito prestitoScaduto = new Prestito(libro2, "Anna Bianchi", dataPassata);

        // Aggiungi manualmente per test (normalmente fatto automaticamente)
        GestionePrestiti gestioneTest = new GestionePrestiti();
        gestioneTest.creaPrestito(libro1, "Mario Rossi");

        // Simula prestito vecchio
        Libro libro4 = new Libro("978-1234567890", "Test Scaduto", "Autore", 2020, 100);
        Prestito vecchio = new Prestito(libro4, "Mario Rossi",
                LocalDate.now().minusDays(40));

        System.out.println(vecchio);

        // Promemoria scadenze
        gestione.mostraPromemoria();

        // Statistiche
        System.out.println("\n=== STATISTICHE ===\n");
        Map<String, Integer> stats = gestione.getStatisticheUtenti();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " +
                    entry.getValue() + " prestiti totali");
        }
    }
}

