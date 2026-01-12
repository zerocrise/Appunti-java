import java.util.Scanner;

public class Biblioteca {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Dati di 3 libri (per ora fissi)
        String titolo1 = "Il Signore degli Anelli", titolo2 = "1984", titolo3 = "Il Nome della Rosa";
        String autore1 = "J.R.R. Tolkien", autore2 = "George Orwell", autore3 = "Umberto Eco";
        int anno1 = 1954, anno2 = 1949, anno3 = 1980;
        String genere1 = "Fantasy", genere2 = "Distopia", genere3 = "Giallo";
        int pagine1 = 1178, pagine2 = 328, pagine3 = 503;
        boolean disp1 = true, disp2 = false, disp3 = true;

        int scelta;

        // Intestazione
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 3.0  ║");
        System.out.println("║      Sistema con Menu e Ricerca        ║");
        System.out.println("╚════════════════════════════════════════╝");

        // Loop menu principale
        do {
            System.out.println("\n═══════════════ MENU ═══════════════════");
            System.out.println("1. Visualizza tutti i libri");
            System.out.println("2. Cerca per titolo");
            System.out.println("3. Filtra per genere");
            System.out.println("4. Filtra per anno");
            System.out.println("5. Mostra libri disponibili");
            System.out.println("6. Statistiche biblioteca");
            System.out.println("0. Esci");
            System.out.println("════════════════════════════════════════");
            System.out.print("Scelta: ");
            scelta = input.nextInt();
            input.nextLine(); // Consuma invio

            switch (scelta) {
                case 1: // Visualizza tutti
                    System.out.println("\n📚 CATALOGO COMPLETO:");
                    System.out.println("─────────────────────────────────────────");

                    System.out.println("1. " + titolo1);
                    System.out.println("   Autore: " + autore1 + " | Anno: " + anno1);
                    System.out.println("   Genere: " + genere1 + " | Pagine: " + pagine1);
                    System.out.println("   Stato: " + (disp1 ? "✅ Disponibile" : "❌ Non disponibile"));
                    System.out.println();

                    System.out.println("2. " + titolo2);
                    System.out.println("   Autore: " + autore2 + " | Anno: " + anno2);
                    System.out.println("   Genere: " + genere2 + " | Pagine: " + pagine2);
                    System.out.println("   Stato: " + (disp2 ? "✅ Disponibile" : "❌ Non disponibile"));
                    System.out.println();

                    System.out.println("3. " + titolo3);
                    System.out.println("   Autore: " + autore3 + " | Anno: " + anno3);
                    System.out.println("   Genere: " + genere3 + " | Pagine: " + pagine3);
                    System.out.println("   Stato: " + (disp3 ? "✅ Disponibile" : "❌ Non disponibile"));
                    break;

                case 2: // Cerca per titolo
                    System.out.print("\n🔍 Inserisci titolo da cercare: ");
                    String ricerca = input.nextLine();

                    System.out.println("\n📖 RISULTATI RICERCA:");
                    System.out.println("─────────────────────────────────────────");

                    boolean trovato = false;

                    if (titolo1.toLowerCase().contains(ricerca.toLowerCase())) {
                        System.out.println("✓ " + titolo1 + " - " + autore1 + " (" + anno1 + ")");
                        trovato = true;
                    }
                    if (titolo2.toLowerCase().contains(ricerca.toLowerCase())) {
                        System.out.println("✓ " + titolo2 + " - " + autore2 + " (" + anno2 + ")");
                        trovato = true;
                    }
                    if (titolo3.toLowerCase().contains(ricerca.toLowerCase())) {
                        System.out.println("✓ " + titolo3 + " - " + autore3 + " (" + anno3 + ")");
                        trovato = true;
                    }

                    if (!trovato) {
                        System.out.println("❌ Nessun libro trovato con quel titolo");
                    }
                    break;

                case 3: // Filtra per genere
                    System.out.print("\n📚 Inserisci genere: ");
                    String genereRicerca = input.nextLine();

                    System.out.println("\n📖 LIBRI DEL GENERE: " + genereRicerca);
                    System.out.println("─────────────────────────────────────────");

                    int contatore = 0;

                    if (genere1.equalsIgnoreCase(genereRicerca)) {
                        System.out.println("• " + titolo1 + " - " + autore1);
                        contatore++;
                    }
                    if (genere2.equalsIgnoreCase(genereRicerca)) {
                        System.out.println("• " + titolo2 + " - " + autore2);
                        contatore++;
                    }
                    if (genere3.equalsIgnoreCase(genereRicerca)) {
                        System.out.println("• " + titolo3 + " - " + autore3);
                        contatore++;
                    }

                    System.out.println("\nTotale: " + contatore + " libro/i");
                    break;

                case 4: // Filtra per anno
                    System.out.print("\n📅 Anno di pubblicazione: ");
                    int annoRicerca = input.nextInt();

                    System.out.println("\n📖 LIBRI PUBBLICATI NEL " + annoRicerca + ":");
                    System.out.println("─────────────────────────────────────────");

                    boolean trovatoAnno = false;

                    if (anno1 == annoRicerca) {
                        System.out.println("• " + titolo1 + " - " + autore1);
                        trovatoAnno = true;
                    }
                    if (anno2 == annoRicerca) {
                        System.out.println("• " + titolo2 + " - " + autore2);
                        trovatoAnno = true;
                    }
                    if (anno3 == annoRicerca) {
                        System.out.println("• " + titolo3 + " - " + autore3);
                        trovatoAnno = true;
                    }

                    if (!trovatoAnno) {
                        System.out.println("❌ Nessun libro di quell'anno");
                    }
                    break;

                case 5: // Mostra disponibili
                    System.out.println("\n✅ LIBRI DISPONIBILI:");
                    System.out.println("─────────────────────────────────────────");

                    int disponibili = 0;

                    if (disp1) {
                        System.out.println("• " + titolo1 + " - " + autore1);
                        disponibili++;
                    }
                    if (disp2) {
                        System.out.println("• " + titolo2 + " - " + autore2);
                        disponibili++;
                    }
                    if (disp3) {
                        System.out.println("• " + titolo3 + " - " + autore3);
                        disponibili++;
                    }

                    System.out.println("\nDisponibili: " + disponibili + " su 3");
                    break;

                case 6: // Statistiche
                    int totalePagine = pagine1 + pagine2 + pagine3;
                    double mediaPagine = totalePagine / 3.0;
                    int annoMin = Math.min(anno1, Math.min(anno2, anno3));
                    int annoMax = Math.max(anno1, Math.max(anno2, anno3));
                    System.out.println("\n📊 STATISTICHE BIBLIOTECA:");
                    System.out.println("─────────────────────────────────────────");
                    System.out.println("Totale libri: 3");
                    System.out.println("Totale pagine: " + totalePagine);
                    System.out.println("Media pagine: " + String.format("%.1f", mediaPagine));
                    System.out.println("Libro più antico: " + annoMin);
                    System.out.println("Libro più recente: " + annoMax);
                    System.out.println("Libri disponibili: " + ((disp1 ? 1 : 0) + (disp2 ? 1 : 0) + (disp3 ? 1 : 0)));
                    break;
                case 0: // Esci
                    System.out.println("\n👋 Grazie per aver usato Biblioteca Personale!");
                    System.out.println("   Arrivederci!");
                    break;

                default:
                    System.out.println("\n❌ Scelta non valida! Riprova.");
            }

        } while (scelta != 0);

        input.close();
    }
}
