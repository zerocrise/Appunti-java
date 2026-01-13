import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    
    private static ArrayList<Pubblicazione> biblioteca = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        int scelta;
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 8.0  ║");
        System.out.println("║    Polimorfismo e Classi Astratte      ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        do {
            System.out.println("\n═══════════════ MENU ═══════════════════");
            System.out.println("1. Aggiungi Libro");
            System.out.println("2. Aggiungi Rivista");
            System.out.println("3. Aggiungi Fumetto");
            System.out.println("4. Visualizza tutte le pubblicazioni");
            System.out.println("5. Presta pubblicazione");
            System.out.println("6. Restituisci pubblicazione");
            System.out.println("7. Valuta libro");
            System.out.println("8. Mostra sconti disponibili");
            System.out.println("9. Statistiche");
            System.out.println("0. Esci");
            System.out.println("════════════════════════════════════════");
            System.out.print("Scelta: ");
            scelta = input.nextInt();
            input.nextLine();
            
            try {
                switch (scelta) {
                    case 1:
                        aggiungiLibro();
                        break;
                    case 2:
                        aggiungiRivista();
                        break;
                    case 3:
                        aggiungiFumetto();
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
                        valutaLibro();
                        break;
                    case 8:
                        mostraSconti();
                        break;
                    case 9:
mostraStatistiche();
break;
case 0:
System.out.println("\n👋 Arrivederci!");
break;
default:
System.out.println("\n❌ Scelta non valida!");
}
} catch (Exception e) {
System.out.println("\n❌ Errore: " + e.getMessage());
input.nextLine();
}
} while (scelta != 0);
    
    input.close();
}

// ... metodi aggiungiLibro(), aggiungiRivista(), aggiungiFumetto() come prima

private static void visualizzaTutte() {
    System.out.println("\n📚 TUTTE LE PUBBLICAZIONI");
    System.out.println("─────────────────────────────────────────");
    
    if (biblioteca.isEmpty()) {
        System.out.println("❌ Biblioteca vuota");
        return;
    }
    
    // Polimorfismo in azione!
    for (int i = 0; i < biblioteca.size(); i++) {
        System.out.println("\n" + (i+1) + ".");
        biblioteca.get(i).stampaInfo();  // Chiama il metodo giusto per ogni tipo
    }
}

private static void prestaPubblicazione() {
    if (biblioteca.isEmpty()) {
        System.out.println("\n❌ Biblioteca vuota");
        return;
    }
    
    System.out.println("\n📤 PRESTA PUBBLICAZIONE");
    System.out.println("─────────────────────────────────────────");
    
    mostraListaDisponibili();
    
    System.out.print("\nNumero pubblicazione (0 per annullare): ");
    int num = input.nextInt();
    
    if (num > 0 && num <= biblioteca.size()) {
        Pubblicazione pub = biblioteca.get(num - 1);
        pub.presta();  // Polimorfismo: funziona per tutti i tipi!
    }
}

private static void restituisciPubblicazione() {
    if (biblioteca.isEmpty()) {
        System.out.println("\n❌ Biblioteca vuota");
        return;
    }
    
    System.out.println("\n📥 RESTITUISCI PUBBLICAZIONE");
    System.out.println("─────────────────────────────────────────");
    
    mostraListaPrestati();
    
    System.out.print("\nNumero pubblicazione (0 per annullare): ");
    int num = input.nextInt();
    
    if (num > 0 && num <= biblioteca.size()) {
        Pubblicazione pub = biblioteca.get(num - 1);
        pub.restituisci();
    }
}

private static void valutaLibro() {
    ArrayList<Libro> libri = new ArrayList<>();
    for (Pubblicazione pub : biblioteca) {
        if (pub instanceof Libro) {
            libri.add((Libro) pub);
        }
    }
    
    if (libri.isEmpty()) {
        System.out.println("\n❌ Nessun libro in biblioteca");
        return;
    }
    
    System.out.println("\n⭐ VALUTA LIBRO");
    System.out.println("─────────────────────────────────────────");
    
    for (int i = 0; i < libri.size(); i++) {
        System.out.printf("%d. %s%n", i+1, libri.get(i).getTitolo());
    }
    
    System.out.print("\nNumero libro: ");
    int num = input.nextInt();
    
    if (num > 0 && num <= libri.size()) {
        System.out.print("Voto (1-5): ");
        int voto = input.nextInt();
        
        Libro libro = libri.get(num - 1);
        libro.aggiungiValutazione(voto);
        System.out.printf("✅ Valutazione aggiunta! Media: %.1f/5%n", 
            libro.getMediaValutazioni());
    }
}

private static void mostraSconti() {
    System.out.println("\n🏷️  SCONTI DISPONIBILI");
    System.out.println("─────────────────────────────────────────");
    
    if (biblioteca.isEmpty()) {
        System.out.println("❌ Biblioteca vuota");
        return;
    }
    
    for (Pubblicazione pub : biblioteca) {
        double sconto = pub.calcolaSconto();
        double prezzoScontato = pub.getPrezzoScontato();
        
        System.out.printf("%s - %s%n", pub.getTipo(), pub.getTitolo());
        System.out.printf("  Prezzo: €%.2f | Sconto: €%.2f | Finale: €%.2f%n", 
            pub.getPrezzo(), sconto, prezzoScontato);
    }
}

private static void mostraStatistiche() {
    if (biblioteca.isEmpty()) {
        System.out.println("\n❌ Biblioteca vuota");
        return;
    }
    
    System.out.println("\n📊 STATISTICHE");
    System.out.println("─────────────────────────────────────────");
    
    int numLibri = 0, numRiviste = 0, numFumetti = 0;
    int disponibili = 0;
    double valTotale = 0, scontiTotali = 0;
    
    for (Pubblicazione pub : biblioteca) {
        // Conta tipi
        if (pub instanceof Libro) numLibri++;
        else if (pub instanceof Rivista) numRiviste++;
        else if (pub instanceof Fumetto) numFumetti++;
        
        // Conta disponibili
        if (pub.isDisponibile()) disponibili++;
        
        // Calcola valori
        valTotale += pub.getPrezzo();
        scontiTotali += pub.calcolaSconto();
    }
    
    System.out.println("Totale pubblicazioni: " + biblioteca.size());
    System.out.println("  - Libri:    " + numLibri);
    System.out.println("  - Riviste:  " + numRiviste);
    System.out.println("  - Fumetti:  " + numFumetti);
    System.out.println();
    System.out.println("Disponibili: " + disponibili + "/" + biblioteca.size());
    System.out.printf("Valore totale: €%.2f%n", valTotale);
    System.out.printf("Sconti totali: €%.2f%n", scontiTotali);
    System.out.printf("Valore scontato: €%.2f%n", valTotale - scontiTotali);
}

private static void mostraListaDisponibili() {
    int num = 1;
    for (int i = 0; i < biblioteca.size(); i++) {
        Pubblicazione pub = biblioteca.get(i);
        if (pub.isDisponibile()) {
            System.out.printf("%d. (%d) %s - %s%n", 
                num++, i+1, pub.getTipo(), pub.getTitolo());
        }
    }
}

private static void mostraListaPrestati() {
    int num = 1;
    for (int i = 0; i < biblioteca.size(); i++) {
        Pubblicazione pub = biblioteca.get(i);
        if (!pub.isDisponibile()) {
            System.out.printf("%d. (%d) %s - %s%n", 
                num++, i+1, pub.getTipo(), pub.getTitolo());
        }
    }
}

// Metodi helper...
private static void aggiungiLibro() {
    System.out.println("\n➕ AGGIUNGI LIBRO");
        System.out.println("─────────────────────────────────────────");

        System.out.print("Titolo: ");
        String titolo = input.nextLine();

        System.out.print("Autore: ");
        String autore = input.nextLine();

        System.out.print("Anno: ");
        int anno = input.nextInt();
        input.nextLine();

        System.out.print("Genere: ");
        String genere = input.nextLine();

        System.out.print("Pagine: ");
        int pagine = input.nextInt();

        System.out.print("Prezzo (€): ");
        double prezzo = input.nextDouble();
        input.nextLine();

        System.out.print("ISBN (opzionale, invio per saltare): ");
        String isbn = input.nextLine();

        Libro libro;
        if (isbn.isEmpty()) {
            libro = new Libro(titolo, autore, anno, genere, pagine, prezzo);
        } else {
            libro = new Libro(titolo, autore, anno, genere, pagine, prezzo,isbn);
        }

        biblioteca.add(libro);
        System.out.println("\n✅ Libro aggiunto con successo!");
}

private static void aggiungiRivista() {
     System.out.println("\n➕ AGGIUNGI RIVISTA");
        System.out.println("─────────────────────────────────────────");

        System.out.print("Titolo: ");
        String titolo = input.nextLine();

        System.out.print("Editore: ");
        String editore = input.nextLine();

        System.out.print("Anno: ");
        int anno = input.nextInt();

        System.out.print("Numero edizione: ");
        int numero = input.nextInt();
        input.nextLine();

        System.out.print("Periodicità (Settimanale/Mensile/Trimestrale): ");
        String periodicita = input.nextLine();

        System.out.print("Pagine: ");
        int pagine = input.nextInt();

        System.out.print("Prezzo (€): ");
        double prezzo = input.nextDouble();

        Rivista rivista = new Rivista(titolo, editore, anno, numero, periodicita, pagine, prezzo);
        biblioteca.add(rivista);
        System.out.println("\n✅ Rivista aggiunta con successo!");
}

private static void aggiungiFumetto() {
    System.out.println("\n➕ AGGIUNGI FUMETTO");
        System.out.println("─────────────────────────────────────────");

        System.out.print("Titolo: ");
        String titolo = input.nextLine();
        System.out.print("Autore: ");
        String autore = input.nextLine();

        System.out.print("Disegnatore: ");
        String disegnatore = input.nextLine();

        System.out.print("Anno: ");
        int anno = input.nextInt();
        input.nextLine();

        System.out.print("Serie: ");
        String serie = input.nextLine();

        System.out.print("Numero volume: ");
        int volume = input.nextInt();

        System.out.print("Pagine: ");
        int pagine = input.nextInt();

        System.out.print("Prezzo (€): ");
        double prezzo = input.nextDouble();

        System.out.print("A colori? (true/false): ");
        boolean colori = input.nextBoolean();

        Fumetto fumetto = new Fumetto(titolo, autore, disegnatore, anno, serie, volume, pagine, prezzo, colori);
        biblioteca.add(fumetto);
        System.out.println("\n✅ Fumetto aggiunto con successo!");
}
}