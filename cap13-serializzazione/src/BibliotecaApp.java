import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaApp {

    private static ArrayList<Pubblicazione> biblioteca = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static boolean modificato = false;  // Traccia modifiche

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 11.0 ║");
        System.out.println("║         Serializzazione Binaria        ║");
        System.out.println("╚════════════════════════════════════════╝");

        // Carica dati all'avvio
        caricaDati();

        int scelta;
        do {
            mostraMenu();
            scelta = leggiInteroSicuro("Scelta: ");

            try {
                gestisciScelta(scelta);
            } catch (Exception e) {
                System.out.println("\n❌ Errore: " + e.getMessage());
                e.printStackTrace();
            }

        } while (scelta != 0);

        // Salva automaticamente se ci sono modifiche
        if (modificato) {
            salvaDati();
        }

        input.close();
        System.out.println("\n👋 Arrivederci!");
    }

    private static void mostraMenu() {
        String indicatoreModifiche = modificato ? " *" : "";

        System.out.println("\n═══════════════ MENU ═══════════════════" + indicatoreModifiche);
        System.out.println("1. Aggiungi Libro");
        System.out.println("2. Aggiungi Rivista");
        System.out.println("3. Aggiungi Fumetto");
        System.out.println("4. Visualizza tutte");
        System.out.println("5. Cerca per titolo");
        System.out.println("6. Presta/Restituisci");
        System.out.println("7. Rimuovi pubblicazione");
        System.out.println("8. Salva");
        System.out.println("9. Crea backup");
        System.out.println("10. Esporta CSV");
        System.out.println("11. Info file");
        System.out.println("0. Esci");
        System.out.println("════════════════════════════════════════");
        if (modificato) {
            System.out.println("* = Modifiche non salvate");
        }
    }

    private static void gestisciScelta(int scelta) throws Exception {
        switch (scelta) {
            case 1:
                aggiungiLibro();
                modificato = true;
                break;
            case 2:
                aggiungiRivista();
                modificato = true;
                break;
            case 3:
                aggiungiFumetto();
                modificato = true;
                break;
            case 4:
                visualizzaTutte();
                break;
            case 5:
                cercaPerTitolo();
                break;
            case 6:
                gestisciPrestito();
                modificato = true;
                break;
            case 7:
                rimuoviPubblicazione();
                modificato = true;
                break;
            case 8:
                salvaDati();
                break;
            case 9:
                creaBackup();
                break;
            case 10:
                esportaCSV();
                break;
            case 11:
                mostraInfoFile();
                break;
            case 0:
                if (modificato) {
                    System.out.print("⚠️  Ci sono modifiche non salvate. Salvare? (s/n): ");
                    String risposta = input.nextLine();
                    if (risposta.equalsIgnoreCase("s")) {
                        salvaDati();
                    }
                }
                break;
            default:
                System.out.println("❌ Scelta non valida");
        }
    }

    private static void caricaDati() {
        try {
            biblioteca = BibliotecaSerializer.carica();
            modificato = false;
        } catch (Exception e) {
            System.out.println("⚠️  Impossibile caricare: " + e.getMessage());
            System.out.println("   Partenza con biblioteca vuota");
            biblioteca = new ArrayList<>();
        }
    }

    private static void salvaDati() {
        try {
            BibliotecaSerializer.salva(biblioteca);
            modificato = false;
        } catch (Exception e) {
            System.out.println("❌ Errore salvataggio: " + e.getMessage());
        }
    }

    private static void creaBackup() {
        try {
            BibliotecaSerializer.creaBackup(biblioteca);
        } catch (Exception e) {
            System.out.println("❌ Errore backup: " + e.getMessage());
        }
    }

    private static void esportaCSV() {
        try {
            System.out.print("Nome file CSV: ");
            String nomeFile = input.nextLine();
            if (!nomeFile.endsWith(".csv")) {
                nomeFile += ".csv";
            }
            BibliotecaSerializer.esportaCSV(biblioteca, nomeFile);
        } catch (Exception e) {
            System.out.println("❌ Errore esportazione: " + e.getMessage());
        }
    }

    private static void mostraInfoFile() {
        BibliotecaSerializer.mostraInfo("biblioteca.ser");
    }

    private static void aggiungiLibro() {
        System.out.println("\n➕ AGGIUNGI LIBRO");
        System.out.println("─────────────────────────────────────────");

        String titolo = leggiStringaNonVuota("Titolo: ");
        String autore = leggiStringaNonVuota("Autore: ");
        int anno = leggiInteroSicuro("Anno: ");

        System.out.print("Genere: ");
        String genere = input.nextLine();

        int pagine = leggiInteroPositivo("Pagine: ");
        double prezzo = leggiDoublePositivo("Prezzo (€): ");

        try {
            Libro libro = new Libro(titolo, autore, anno, genere, pagine, prezzo);
            biblioteca.add(libro);
            System.out.println("✅ Libro aggiunto con successo");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore: " + e.getMessage());
        }
    }

    // Altri metodi simili per Rivista e Fumetto...

    private static void visualizzaTutte() {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        System.out.println("\n📚 TUTTE LE PUBBLICAZIONI (" + biblioteca.size() + ")");
        System.out.println("─────────────────────────────────────────");

        for (int i = 0; i < biblioteca.size(); i++) {
            System.out.println("\n" + (i+1) + ".");
            biblioteca.get(i).stampaInfo();
        }
    }

    private static void cercaPerTitolo() {
        System.out.print("\n🔍 Titolo da cercare: ");
        String ricerca = input.nextLine();

        boolean trovato = false;
        for (Pubblicazione pub : biblioteca) {
            if (pub.getTitolo().toLowerCase().contains(ricerca.toLowerCase())) {
                pub.stampaInfo();
                trovato = true;
            }
        }

        if (!trovato) {
            System.out.println("❌ Nessuna pubblicazione trovata");
        }
    }

    private static void gestisciPrestito() {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        mostraListaNumerata();

        int indice = leggiInteroSicuro("Numero pubblicazione: ") - 1;

        if (indice < 0 || indice >= biblioteca.size()) {
            System.out.println("❌ Numero non valido");
            return;
        }

        Pubblicazione pub = biblioteca.get(indice);

        if (pub.isDisponibile()) {
            pub.presta();
        } else {
            pub.restituisci();
        }
    }

    private static void rimuoviPubblicazione() {
        if (biblioteca.isEmpty()) {
            System.out.println("\n📚 Biblioteca vuota");
            return;
        }

        mostraListaNumerata();

        int indice = leggiInteroSicuro("Numero pubblicazione da rimuovere: ") - 1;

        if (indice < 0 || indice >= biblioteca.size()) {
            System.out.println("❌ Numero non valido");
            return;
        }

        Pubblicazione rimossa = biblioteca.remove(indice);
        System.out.println("✅ Rimossa: " + rimossa.getTitolo());
    }

    private static void mostraListaNumerata() {
        for (int i = 0; i < biblioteca.size(); i++) {
            Pubblicazione pub = biblioteca.get(i);
            String stato = pub.isDisponibile() ? "✅" : "❌";
            System.out.printf("%d. %s [%s] %s%n",
                    i+1, pub.getTipo(), stato, pub.getTitolo());
        }
    }

    // Metodi di utilità per input sicuro (come nei capitoli precedenti)

    private static int leggiInteroSicuro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Inserisci un numero intero valido");
            }
        }
    }

    private static int leggiInteroPositivo(String prompt) {
        while (true) {
            int valore = leggiInteroSicuro(prompt);
            if (valore > 0) return valore;
            System.out.println("❌ Il valore deve essere positivo");
        }
    }

    private static double leggiDoublePositivo(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double valore = Double.parseDouble(input.nextLine());
                if (valore >= 0) return valore;
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
            if (!valore.isEmpty()) return valore;
            System.out.println("❌ Il campo non può essere vuoto");
        }
    }

    // Aggiungi metodi per Rivista e Fumetto...

    private static void aggiungiRivista() {
        // Implementazione simile ad aggiungiLibro
    }

    private static void aggiungiFumetto() {
        // Implementazione simile ad aggiungiLibro
    }
}
