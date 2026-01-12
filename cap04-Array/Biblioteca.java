import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca4 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ArrayList per memorizzare i dati
        ArrayList < String > titoli = new ArrayList < > ();
        ArrayList < String > autori = new ArrayList < > ();
        ArrayList < Integer > anni = new ArrayList < > ();
        ArrayList < String > generi = new ArrayList < > ();
        ArrayList < Integer > pagine = new ArrayList < > ();
        ArrayList < Boolean > disponibili = new ArrayList < > ();

        int scelta;

        // Intestazione
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 4.0  ║");
        System.out.println("║      Gestione Dinamica con ArrayList   ║");
        System.out.println("╚════════════════════════════════════════╝");

        // Menu principale
        do {
            System.out.println("\n═══════════════ MENU ═══════════════════");
            System.out.println("1. Aggiungi libro");
            System.out.println("2. Visualizza tutti i libri");
            System.out.println("3. Cerca per titolo");
            System.out.println("4. Filtra per genere");
            System.out.println("5. Rimuovi libro");
            System.out.println("6. Modifica disponibilità");
            System.out.println("7. Statistiche biblioteca");
            System.out.println("0. Esci");
            System.out.println("════════════════════════════════════════");
            System.out.print("Scelta: ");
            scelta = input.nextInt();
            input.nextLine(); // Consuma invio

            switch (scelta) {
                case 1: // Aggiungi libro
                    System.out.println("\n➕ AGGIUNGI NUOVO LIBRO");
                    System.out.println("─────────────────────────────────────────");

                    System.out.print("Titolo: ");
                    String nuovoTitolo = input.nextLine();

                    System.out.print("Autore: ");
                    String nuovoAutore = input.nextLine();

                    System.out.print("Anno: ");
                    int nuovoAnno = input.nextInt();
                    input.nextLine();

                    System.out.print("Genere: ");
                    String nuovoGenere = input.nextLine();

                    System.out.print("Pagine: ");
                    int nuovePagine = input.nextInt();

                    System.out.print("Disponibile (true/false): ");
                    boolean nuovaDisp = input.nextBoolean();

                    // Aggiunta agli ArrayList
                    titoli.add(nuovoTitolo);
                    autori.add(nuovoAutore);
                    anni.add(nuovoAnno);
                    generi.add(nuovoGenere);
                    pagine.add(nuovePagine);
                    disponibili.add(nuovaDisp);

                    System.out.println("\n✅ Libro aggiunto con successo!");
                    System.out.println("   Totale libri in biblioteca: " + titoli.size());
                    break;

                case 2: // Visualizza tutti
                    System.out.println("\n📚 CATALOGO COMPLETO");
                    System.out.println("─────────────────────────────────────────");

                    if (titoli.isEmpty()) {
                        System.out.println("❌ Nessun libro in biblioteca");
                    } else {
                        for (int i = 0; i < titoli.size(); i++) {
                            System.out.println("\n" + (i + 1) + ". " + titoli.get(i));
                            System.out.println("   Autore: " + autori.get(i));
                            System.out.println("   Anno: " + anni.get(i) +
                                " | Genere: " + generi.get(i));
                            System.out.println("   Pagine: " + pagine.get(i));
                            System.out.println("   Stato: " +
                                (disponibili.get(i) ? "✅ Disponibile" : "❌ Non disponibile"));
                        }
                        System.out.println("\n─────────────────────────────────────────");
                        System.out.println("Totale: " + titoli.size() + " libro/i");
                    }
                    break;

                case 3: // Cerca per titolo
                    System.out.print("\n🔍 Inserisci titolo da cercare: ");
                    String ricerca = input.nextLine();

                    System.out.println("\n📖 RISULTATI RICERCA:");
                    System.out.println("─────────────────────────────────────────");

                    boolean trovato = false;

                    for (int i = 0; i < titoli.size(); i++) {
                        if (titoli.get(i).toLowerCase().contains(ricerca.toLowerCase())) {
                            System.out.println("\n✓ " + titoli.get(i));
                            System.out.println("  Autore: " + autori.get(i));
                            System.out.println("  Anno: " + anni.get(i) +
                                " | Pagine: " + pagine.get(i));
                            System.out.println("  Posizione: #" + (i + 1));
                            trovato = true;
                        }
                    }

                    if (!trovato) {
                        System.out.println("❌ Nessun libro trovato");
                    }
                    break;

                case 4: // Filtra per genere
                    System.out.print("\n📚 Inserisci genere: ");
                    String genereRicerca = input.nextLine();

                    System.out.println("\n📖 LIBRI DEL GENERE: " + genereRicerca);
                    System.out.println("─────────────────────────────────────────");

                    int contatore = 0;

                    for (int i = 0; i < generi.size(); i++) {
                        if (generi.get(i).equalsIgnoreCase(genereRicerca)) {
                            System.out.println("• " + titoli.get(i) + " - " + autori.get(i));
                            contatore++;
                        }
                    }

                    if (contatore == 0) {
                        System.out.println("❌ Nessun libro di questo genere");
                    } else {
                        System.out.println("\nTotale: " + contatore + " libro/i");
                    }
                    break;

                case 5: // Rimuovi libro
                    if (titoli.isEmpty()) {
                        System.out.println("\n❌ Nessun libro da rimuovere");
                        break;
                    }

                    System.out.println("\n🗑️  RIMUOVI LIBRO");
                    System.out.println("─────────────────────────────────────────");

                    // Mostra lista con numeri
                    for (int i = 0; i < titoli.size(); i++) {
                        System.out.println((i + 1) + ". " + titoli.get(i));
                    }

                    System.out.print("\nInserisci numero libro da rimuovere (0 per annullare): ");
                    int numRimuovi = input.nextInt();

                    if (numRimuovi > 0 && numRimuovi <= titoli.size()) {
                        int index = numRimuovi - 1;
                        String titoloRimosso = titoli.get(index);

                        titoli.remove(index);
                        autori.remove(index);
                        anni.remove(index);
                        generi.remove(index);
                        pagine.remove(index);
                        disponibili.remove(index);

                        System.out.println("✅ Libro \"" + titoloRimosso + "\" rimosso con successo!");
                    } else if (numRimuovi != 0) {
                        System.out.println("❌ Numero non valido");
                    }
                    break;

                case 6: // Modifica disponibilità
                    if (titoli.isEmpty()) {
                        System.out.println("\n❌ Nessun libro in biblioteca");
                        break;
                    }

                    System.out.println("\n🔄 MODIFICA DISPONIBILITÀ");
                    System.out.println("─────────────────────────────────────────");

                    for (int i = 0; i < titoli.size(); i++) {
                        String stato = disponibili.get(i) ? "✅" : "❌";
                        System.out.println((i + 1) + ". " + titoli.get(i) + " " + stato);
                    }

                    System.out.print("\nInserisci numero libro (0 per annullare): ");
                    int numMod = input.nextInt();

                    if (numMod > 0 && numMod <= titoli.size()) {
                        int index = numMod - 1;
                        boolean statoAttuale = disponibili.get(index);
                        disponibili.set(index, !statoAttuale);

                        String nuovoStato = !statoAttuale ? "Disponibile" : "Non disponibile";
                        System.out.println("✅ Stato aggiornato: " + nuovoStato);
                    } else if (numMod != 0) {
                        System.out.println("❌ Numero non valido");
                    }
                    break;

                case 7: // Statistiche
                    if (titoli.isEmpty()) {
                        System.out.println("\n❌ Nessun libro in biblioteca");
                        break;
                    }

                    System.out.println("\n📊 STATISTICHE BIBLIOTECA");
                    System.out.println("─────────────────────────────────────────");

                    // Totale pagine e media
                    int totalePagine = 0;
                    for (int p: pagine) {
                        totalePagine += p;
                    }
                    double mediaPagine = totalePagine / (double) pagine.size();

                    // Anno più antico e più recente
                    int annoMin = anni.get(0);
                    int annoMax = anni.get(0);
                    for (int anno: anni) {
                        if (anno < annoMin) annoMin = anno;
                        if (anno > annoMax) annoMax = anno;
                    }

                    // Conteggio disponibili
                    int contDisp = 0;
                    for (boolean disp: disponibili) {
                        if (disp) contDisp++;
                    }

                    // Libro più lungo
                    int maxPagine = pagine.get(0);
                    int indexMax = 0;
                    for (int i = 1; i < pagine.size(); i++) {
                        if (pagine.get(i) > maxPagine) {
                            maxPagine = pagine.get(i);
                            indexMax = i;
                        }
                    }

                    System.out.println("Totale libri:        " + titoli.size());
                    System.out.println("Totale pagine:       " + totalePagine);
                    System.out.printf("Media pagine:        %.1f%n", mediaPagine);
                    System.out.println("Libro più antico:    " + annoMin);
                    System.out.println("Libro più recente:   " + annoMax);
                    System.out.println("Libri disponibili:   " + contDisp + "/" + titoli.size());
                    System.out.println("Libro più lungo:     " + titoli.get(indexMax) +
                        " (" + maxPagine + " pagine)");
                    break;

                case 0: // Esci
                    System.out.println("\n👋 Grazie per aver usato Biblioteca Personale!");
                    System.out.println("   Arrivederci!");
                    break;

                default:
                    System.out.println("\n❌ Scelta non valida!");
            }

        } while (scelta != 0);

        input.close();
    }
}
