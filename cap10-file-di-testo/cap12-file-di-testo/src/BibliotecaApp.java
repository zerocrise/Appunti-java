import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class BibliotecaApp {

    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 10.0 ║");
        System.out.println("║       Persistenza su File CSV          ║");
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
            }

        } while (scelta != 0);

        // Salva dati alla chiusura
        salvaDati();

        input.close();
        System.out.println("\n👋 Arrivederci!");
    }

    private static void mostraMenu() {
        System.out.println("\n═══════════════ MENU ═══════════════════");
        System.out.println("1. Aggiungi Libro");
        System.out.println("2. Visualizza tutte");
        System.out.println("3. Cerca per titolo");
        System.out.println("4. Presta pubblicazione");
        System.out.println("5. Restituisci pubblicazione");
        System.out.println("6. Salva manualmente");
        System.out.println("7. Ricarica da file");
        System.out.println("8. Crea backup");
        System.out.println("0. Esci");
        System.out.println("════════════════════════════════════════");
    }

    private static void gestisciScelta(int scelta) throws Exception {
        switch (scelta) {
            case 1:
                aggiungiLibro();
                break;
            case 2:
                visualizzaTutte();
                break;
            case 3:
                cercaPerTitolo();
                break;
            case 4:
                prestaPubblicazione();
                break;
            case 5:
                restituisciPubblicazione();
                break;
            case 6:
                salvaDati();
                break;
            case 7:
                ricaricaDati();
                break;
            case 8:
                creaBackup();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Scelta non valida");
        }
    }

    private static void caricaDati() {
        try {
            ArrayList<Pubblicazione> pubblicazioni = BibliotecaFileManager.carica();
            biblioteca = new Biblioteca();
            for (Pubblicazione pub : pubblicazioni) {
                // Ricostruisci la biblioteca
                if (pub instanceof Libro) {
                    Libro l = (Libro) pub;
                    biblioteca.aggiungiLibro(l.getTitolo(), l.getAutore(),
                            l.getAnnoPubblicazione(), l.getGenere(),
                            l.getNumeroPagine(), l.getPrezzo());
                }
                // Aggiungi gestione Rivista e Fumetto se necessario
            }
        } catch (IOException e) {
            System.out.println("⚠️  Impossibile caricare dati: " + e.getMessage());
            System.out.println("   Partenza con biblioteca
        pub.setDisponibile(disponibile);
        return pub;
        }

private static String[] parseCSV(String riga) {
        ArrayList<String> campi = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean inVirgolette = false;

        for (int i = 0; i < riga.length(); i++) {
        char c = riga.charAt(i);

        if (c == '"') {
        inVirgolette = !inVirgolette;
        } else if (c == ',' && !inVirgolette) {
        campi.add(campo.toString());
        campo = new StringBuilder();
        } else {
        campo.append(c);
        }
        }
        campi.add(campo.toString());

        return campi.toArray(new String[0]);
        }
