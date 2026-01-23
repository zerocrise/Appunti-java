import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 9.0  ║");
        System.out.println("║      Gestione Robusta degli Errori     ║");
        System.out.println("╚════════════════════════════════════════╝");

        int scelta;

        do {
            mostraMenu();
            scelta = leggiInteroSicuro("Scelta: ");

            try {
                gestisciScelta(scelta);
            } catch (BibliotecaException e) {
                System.out.println("\n❌ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n❌ Errore imprevisto: " + e.getMessage());
                e.printStackTrace();
            }

        } while (scelta != 0);

        input.close();
        System.out.println("\n👋 Arrivederci!");
    }

    private static void mostraMenu() {
        System.out.println("\n═══════════════ MENU ═══════════════════");
        System.out.println("1. Aggiungi Libro");
        System.out.println("2. Cerca per titolo");
        System.out.println("3. Cerca per autore");
        System.out.println("4. Visualizza tutte");
        System.out.println("5. Presta pubblicazione");
        System.out.println("6. Restituisci pubblicazione");
        System.out.println("7. Rimuovi pubblicazione");
        System.out.println("0. Esci");
        System.out.println("════════════════════════════════════════");
    }

    private static void gestisciScelta(int scelta) throws BibliotecaException {
        switch (scelta) {
            case 1:
                aggiungiLibro();
                break;
            case 2:
                cercaPerTitolo();
                break;
            case 3:
                cercaPerAutore();
                break;
            case 4:
                visualizzaTutte();
                break;
            case 5:
                prestaPubblicazione();
                break;
            case 6:
                restituisciPubblicazione();
                break;
            case 7:
                rimuoviPubblicazione();
                break;
            case 0:
                // Esci
                break;
            default:
                System.out.println("❌ Scelta non valida");
        }
    }

    private static void aggiungiLibro() throws DatiNonValidiException {
        System.out.println("\n➕ AGGIUNGI LIBRO");
        System.out.println("─────────────────────────────────────────");

        String titolo = leggiStringaNonVuota("Titolo: ");
        String autore = leggiStringaNonVuota("Autore: ");
        int anno = leggiInteroSicuro("Anno: ");

        System.out.print("Genere: ");
        String genere = input.nextLine();

        int pagine = leggiInteroPositivo("Pagine: ");
        double prezzo = leggiDoublePositivo("Prezzo (€): ");

        biblioteca.aggiungiLibro(titolo, autore, anno, genere, pagine, prezzo);
    }

    private static void cercaPerTitolo() throws LibroNonTrovatoException {
        System.out.print("\n🔍 Titolo da cercare: ");
        String titolo = input.nextLine();

        Pubblicazione pub = biblioteca.cercaPerTitolo(titolo);
        System.out.println("\n✅ TROVATO:");
        pub.stampaInfo();
    }

    private static void cercaPerAutore() throws LibroNonTrovatoException {
        System.out.print("\n🔍 Autore da cercare: ");
        String autore = input.nextLine();

        ArrayList<Pubblicazione> risultati = biblioteca.cercaPerAutore(autore);

        System.out.println("\n✅ TROVATI " + risultati.size() + " LIBRI:");
        for (Pubblicazione pub : risultati) {
            pub.stampaInfo();
        }
    }

    private static void visualizzaTutte() {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        System.out.println("\n📚 TUTTE LE PUBBLICAZIONI");
        System.out.println("─────────────────────────────────────────");

        ArrayList<Pubblicazione> pubs = biblioteca.getPubblicazioni();
        for (int i = 0; i < pubs.size(); i++) {
            System.out.println("\n" + (i + 1) + ".");
            pubs.get(i).stampaInfo();
        }
    }

    private static void prestaPubblicazione()
            throws IndexOutOfBoundsException, PubblicazioneNonDisponibileException {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        mostraListaNumerate();
        int indice = leggiInteroSicuro("Numero pubblicazione: ") - 1;
        biblioteca.presta(indice);
    }

    private static void restituisciPubblicazione()
            throws IndexOutOfBoundsException {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        mostraListaNumerate();
        int indice = leggiInteroSicuro("Numero pubblicazione: ") - 1;
        biblioteca.restituisci(indice);
    }

    private static void rimuoviPubblicazione()
            throws IndexOutOfBoundsException {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        mostraListaNumerate();
        int indice = leggiInteroSicuro("Numero pubblicazione: ") - 1;
        biblioteca.rimuovi(indice);
    }

// Metodi di utilità per input sicuro

    private static int leggiInteroSicuro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = BibliotecaApp.input.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Inserisci un numero intero valido");
            }
        }
    }

    private static int leggiInteroPositivo(String prompt) {
        while (true) {
            int valore = leggiInteroSicuro(prompt);
            if (valore > 0) {
                return valore;
            }
            System.out.println("❌ Il valore deve essere positivo");
        }
    }

    private static double leggiDoublePositivo(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = BibliotecaApp.input.nextLine();
                double valore = Double.parseDouble(input);
                if (valore >= 0) {
                    return valore;
                }
                System.out.println("❌ Il valore deve essere positivo");
            } catch (NumberFormatException e) {
                System.out.println("❌ Inserisci un numero valido");
            }
        }
    }

    private static String leggiStringaNonVuota(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valore = input.nextLine().trim();
            if (!valore.isEmpty()) {
                return valore;
            }
            System.out.println("❌ Il campo non può essere vuoto");
        }
    }

    private static void mostraListaNumerate() {
        ArrayList<Pubblicazione> pubs = biblioteca.getPubblicazioni();
        for (int i = 0; i < pubs.size(); i++) {
            Pubblicazione pub = pubs.get(i);
            String stato = pub.isDisponibile() ? "✅" : "❌";
            System.out.printf("%d. %s %s - %s%n",
                    i + 1, stato, pub.getTipo(), pub.getTitolo());
        }
    }
}
