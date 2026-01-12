import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca5 {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Libro> biblioteca = new ArrayList<>();
        
        int scelta;
        
        // Intestazione
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 5.0  ║");
        System.out.println("║   Programmazione Orientata agli Oggetti║");
        System.out.println("╚════════════════════════════════════════╝");
        
        // Menu principale
        do {
            System.out.println("\n═══════════════ MENU ═══════════════════");
            System.out.println("1. Aggiungi libro");
            System.out.println("2. Visualizza tutti i libri");
            System.out.println("3. Cerca libro");
            System.out.println("4. Filtra per genere");
            System.out.println("5. Presta libro");
            System.out.println("6. Restituisci libro");
            System.out.println("7. Rimuovi libro");
            System.out.println("8. Statistiche");
            System.out.println("0. Esci");
            System.out.println("════════════════════════════════════════");
            System.out.print("Scelta: ");
            scelta = input.nextInt();
            input.nextLine();
            
            switch (scelta) {
                case 1:  // Aggiungi libro
                    System.out.println("\n➕ AGGIUNGI NUOVO LIBRO");
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
                    
                    // Creare e aggiungere il libro
                    Libro nuovoLibro = new Libro(titolo, autore, anno, genere, pagine);
                    biblioteca.add(nuovoLibro);
                    
                    System.out.println("\n✅ Libro aggiunto con successo!");
                    System.out.println("   Totale libri: " + biblioteca.size());
                    break;
                    
                case 2:  // Visualizza tutti
                    System.out.println("\n📚 CATALOGO COMPLETO");
                    System.out.println("─────────────────────────────────────────");
                    
                    if (biblioteca.isEmpty()) {
                        System.out.println("❌ Nessun libro in biblioteca");
                    } else {
                        for (int i = 0; i < biblioteca.size(); i++) {
                            System.out.print((i+1) + ". ");
                            biblioteca.get(i).stampaInfoCompatta();
                        }
                        System.out.println("\nTotale: " + biblioteca.size() + " libro/i");
                    }
                    break;
                    
                case 3:  // Cerca libro
                    System.out.print("\n🔍 Parola chiave: ");
                    String ricerca = input.nextLine();
                    
                    System.out.println("\n📖 RISULTATI RICERCA:");
                    System.out.println("─────────────────────────────────────────");
                    
                    int trovati = 0;
                    for (Libro libro : biblioteca) {
                        if (libro.contiene(ricerca)) {
                            libro.stampaInfo();
                            trovati++;
                        }
                    }
                    
                    if (trovati == 0) {
                        System.out.println("❌ Nessun libro trovato");
                    } else {
                        System.out.println("\nTrovati: " + trovati + " libro/i");
                    }
                    break;
                    
                case 4:  // Filtra per genere
                    System.out.print("\n📚 Genere: ");
                    String genFiltro = input.nextLine();
                    
                    System.out.println("\n📖 LIBRI DEL GENERE: " + genFiltro);
                    System.out.println("─────────────────────────────────────────");
                    
                    int contatore = 0;
                    for (Libro libro : biblioteca) {
                        if (libro.genere.equalsIgnoreCase(genFiltro)) {
                            libro.stampaInfoCompatta();
                            contatore++;
                        }
                    }
                    
                    if (contatore == 0) {
                        System.out.println("❌ Nessun libro di questo genere");
                    } else {
                        System.out.println("\nTotale: " + contatore + " libro/i");
                    }
                    break;
                    
                case 5:  // Presta libro
                    if (biblioteca.isEmpty()) {
                        System.out.println("\n❌ Nessun libro in biblioteca");
                        break;
                    }
                    
                    System.out.println("\n📤 PRESTA LIBRO");
                    System.out.println("─────────────────────────────────────────");
                    // Mostra solo disponibili
                ArrayList<Integer> disponibili = new ArrayList<>();
                for (int i = 0; i < biblioteca.size(); i++) {
                    if (biblioteca.get(i).disponibile) {
                        disponibili.add(i);
                        System.out.println((disponibili.size()) + ". " + 
                            biblioteca.get(i).titolo);
                    }
                }
                
                if (disponibili.isEmpty()) {
                    System.out.println("❌ Nessun libro disponibile");
                } else {
                    System.out.print("\nNumero libro (0 per annullare): ");
                    int numPrestito = input.nextInt();
                    
                    if (numPrestito > 0 && numPrestito <= disponibili.size()) {
                        int index = disponibili.get(numPrestito - 1);
                        biblioteca.get(index).presta();
                    }
                }
                break;
                
            case 6:  // Restituisci libro
                if (biblioteca.isEmpty()) {
                    System.out.println("\n❌ Nessun libro in biblioteca");
                    break;
                }
                
                System.out.println("\n📥 RESTITUISCI LIBRO");
                System.out.println("─────────────────────────────────────────");
                
                // Mostra solo prestati
                ArrayList<Integer> prestati = new ArrayList<>();
                for (int i = 0; i < biblioteca.size(); i++) {
                    if (!biblioteca.get(i).disponibile) {
                        prestati.add(i);
                        System.out.println((prestati.size()) + ". " + 
                            biblioteca.get(i).titolo);
                    }
                }
                
                if (prestati.isEmpty()) {
                    System.out.println("❌ Nessun libro prestato");
                } else {
                    System.out.print("\nNumero libro (0 per annullare): ");
                    int numRest = input.nextInt();
                    
                    if (numRest > 0 && numRest <= prestati.size()) {
                        int index = prestati.get(numRest - 1);
                        biblioteca.get(index).restituisci();
                    }
                }
                break;
                
            case 7:  // Rimuovi libro
                if (biblioteca.isEmpty()) {
                    System.out.println("\n❌ Nessun libro da rimuovere");
                    break;
                }
                
                System.out.println("\n🗑️  RIMUOVI LIBRO");
                System.out.println("─────────────────────────────────────────");
                
                for (int i = 0; i < biblioteca.size(); i++) {
                    System.out.println((i+1) + ". " + biblioteca.get(i).titolo);
                }
                
                System.out.print("\nNumero libro (0 per annullare): ");
                int numRim = input.nextInt();
                
                if (numRim > 0 && numRim <= biblioteca.size()) {
                    Libro rimosso = biblioteca.remove(numRim - 1);
                    System.out.println("✅ \"" + rimosso.titolo + "\" rimosso");
                }
                break;
                
            case 8:  // Statistiche
                if (biblioteca.isEmpty()) {
                    System.out.println("\n❌ Nessun libro in biblioteca");
                    break;
                }
                
                System.out.println("\n📊 STATISTICHE BIBLIOTECA");
                System.out.println("─────────────────────────────────────────");
                
                int totPagine = 0;
                int annoMin = biblioteca.get(0).annoPubblicazione;
                int annoMax = biblioteca.get(0).annoPubblicazione;
                int maxPag = biblioteca.get(0).numeroPagine;
                Libro libroLungo = biblioteca.get(0);
                int contDisp = 0;
                
                for (Libro libro : biblioteca) {
                    totPagine += libro.numeroPagine;
                    if (libro.annoPubblicazione < annoMin) 
                        annoMin = libro.annoPubblicazione;
                    if (libro.annoPubblicazione > annoMax) 
                        annoMax = libro.annoPubblicazione;
                    if (libro.numeroPagine > maxPag) {
                        maxPag = libro.numeroPagine;
                        libroLungo = libro;
                    }
                    if (libro.disponibile) contDisp++;
                }
                
                double mediaPag = totPagine / (double) biblioteca.size();
                
                System.out.println("Totale libri:        " + biblioteca.size());
                System.out.println("Totale pagine:       " + totPagine);
                System.out.printf("Media pagine:        %.1f%n", mediaPag);
                System.out.println("Libro più antico:    " + annoMin);
                System.out.println("Libro più recente:   " + annoMax);
                System.out.println("Libri disponibili:   " + contDisp + "/" + biblioteca.size());
                System.out.println("Libro più lungo:     " + libroLungo.titolo + 
                                 " (" + maxPag + " pagine)");
                break;
                
            case 0:  // Esci
                System.out.println("\n👋 Grazie per aver usato Biblioteca Personale!");
                break;
                
            default:
                System.out.println("\n❌ Scelta non valida!");
        }
        
    } while (scelta != 0);
    
    input.close();
}
}