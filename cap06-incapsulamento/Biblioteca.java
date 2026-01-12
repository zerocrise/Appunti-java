import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Libro> biblioteca = new ArrayList<>();
        
        int scelta;
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 6.0  ║");
        System.out.println("║        Incapsulamento Completo        ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        do {
            System.out.println("\n═══════════════ MENU ═══════════════════");
            System.out.println("1. Aggiungi libro");
            System.out.println("2. Visualizza tutti i libri");
            System.out.println("3. Cerca libro");
            System.out.println("4. Modifica prezzo");
            System.out.println("5. Applica sconto");
            System.out.println("6. Presta/Restituisci");
            System.out.println("7. Statistiche");
            System.out.println("0. Esci");
            System.out.println("════════════════════════════════════════");
            System.out.print("Scelta: ");
            scelta = input.nextInt();
            input.nextLine();
            
            try {
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
                        
                        System.out.print("Prezzo (€): ");
                        double prezzo = input.nextDouble();
                        
                        // Crea libro (validazione automatica nel costruttore)
                        Libro nuovoLibro = new Libro(titolo, autore, anno, genere, pagine, prezzo);
                        biblioteca.add(nuovoLibro);
                        
                        System.out.println("\n✅ Libro aggiunto con successo!");
                        System.out.println("   Prezzo con IVA: €" + 
                            String.format("%.2f", nuovoLibro.getPrezzoConIVA()));
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
                            
                            double totale = Biblioteca.calcolaPrezzoTotale(biblioteca);
                            System.out.println("\n─────────────────────────────────────────");
                            System.out.printf("Valore totale: €%.2f%n", totale);
                            System.out.println("Totale libri: " + biblioteca.size());
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
                        }
                        break;
                        
                    case 4:  // Modifica prezzo
                        if (biblioteca.isEmpty()) {
                            System.out.println("\n❌ Nessun libro in biblioteca");
                            break;
                        }
                        
                        System.out.println("\n💰 MODIFICA PREZZO");
                        System.out.println("─────────────────────────────────────────");
                        
                        for (int i = 0; i < biblioteca.size(); i++) {
                            System.out.printf("%d. %s - €%.2f%n", 
                                i+1, 
                                biblioteca.get(i).getTitolo(),
                                biblioteca.get(i).getPrezzo());
                        }
                        
                        System.out.print("\nNumero libro: ");
                        int numPrezzo = input.nextInt();
                        
                        if (numPrezzo > 0 && numPrezzo <= biblioteca.size()) {
                            Libro libro = biblioteca.get(numPrezzo - 1);
                            System.out.print("Nuovo prezzo (€): ");
                            double nuovoPrezzo = input.nextDouble();
                            
                            libro.setPrezzo(nuovoPrezzo);  // Validazione automatica
                            System.out.println("✅ Prezzo aggiornato!");
                            System.out.printf("   Prezzo con IVA: €%.2f%n", 
                                libro.getPrezzoConIVA());
                        }
                        break;
                        
                    case 5:  // Applica sconto
                        if (biblioteca.isEmpty()) {
                            System.out.println("\n❌ Nessun libro in biblioteca");
                            break;
                        }
                        
                        System.out.println("\n🏷️  APPLICA SCONTO");
                        System.out.println("─────────────────────────────────────────");
                        
                        for (int i = 0; i < biblioteca.size(); i++) {
                            System.out.printf("%d. %s - €%.2f%n", 
                                i+1, 
                                biblioteca.get(i).getTitolo(),
                                biblioteca.get(i).getPrezzo());
                        }
                        
                        System.out.print("\nNumero libro: ");
                        int numSconto = input.nextInt();
                        
                        if (numSconto > 0 && numSconto <= biblioteca.size()) {
                            Libro libro = biblioteca.get(numSconto - 1);
                            double prezzoOriginale = libro.getPrezzo();
                            
                            System.out.print("Percentuale sconto (0-100): ");
                            double percentuale = input.nextDouble();
                            
                            libro.applicaSconto(percentuale);
                            System.out.println("✅ Sconto applicato!");
                            System.out.printf("   Prezzo originale: €%.2f%n", prezzoOriginale);
                            System.out.printf("   Nuovo prezzo: €%.2f%n", libro.getPrezzo());
                            System.out.printf("   Risparmio: €%.2f%n", 
                                prezzoOriginale - libro.getPrezzo());
                        }
                        break;
                        
                    case 6:  // Presta/Restituisci
                        if (biblioteca.isEmpty()) {
                            System.out.println("\n❌ Nessun libro in biblioteca");
                            break;
                        }
                        
                        System.out.println("\n📤📥 GESTIONE PRESTITI");
                        System.out.println("─────────────────────────────────────────");
                        
                        for (int i = 0; i < biblioteca.size(); i++) {
                            String stato = biblioteca.get(i).isDisponibile() ? "✅" : "❌";
System.out.printf("%d. %s %s%n",i+1, stato, biblioteca.get(i).getTitolo());
}
                    System.out.print("\nNumero libro: ");
                    int numPrestito = input.nextInt();
                    
                    if (numPrestito > 0 && numPrestito <= biblioteca.size()) {
                        Libro libro = biblioteca.get(numPrestito - 1);
                        
                        System.out.println("1. Presta");
                        System.out.println("2. Restituisci");
                        System.out.print("Scelta: ");
                        int azionePrestito = input.nextInt();
                        
                        if (azionePrestito == 1) {
                            libro.presta();
                        } else if (azionePrestito == 2) {
                            libro.restituisci();
                        }
                    }
                    break;
                    
                case 7:  // Statistiche
                    if (biblioteca.isEmpty()) {
                        System.out.println("\n❌ Nessun libro in biblioteca");
                        break;
                    }
                    
                    System.out.println("\n📊 STATISTICHE BIBLIOTECA");
                    System.out.println("─────────────────────────────────────────");
                    
                    double totaleValore = Biblioteca.calcolaPrezzoTotale(biblioteca);
                    Libro piuCostoso = Biblioteca.trovaPiuCostoso(biblioteca);
                    
                    int totPagine = 0;
                    int disponibili = 0;
                    int annoMin = biblioteca.get(0).getAnnoPubblicazione();
                    int annoMax = biblioteca.get(0).getAnnoPubblicazione();
                    
                    for (Libro libro : biblioteca) {
                        totPagine += libro.getNumeroPagine();
                        if (libro.isDisponibile()) disponibili++;
                        if (libro.getAnnoPubblicazione() < annoMin) 
                            annoMin = libro.getAnnoPubblicazione();
                        if (libro.getAnnoPubblicazione() > annoMax) 
                            annoMax = libro.getAnnoPubblicazione();
                    }
                    
                    double mediaPagine = totPagine / (double) biblioteca.size();
                    
                    System.out.println("Totale libri:        " + biblioteca.size());
                    System.out.printf("Valore biblioteca:   €%.2f%n", totaleValore);
                    System.out.printf("Valore con IVA:      €%.2f%n", totaleValore * 1.22);
                    System.out.println("Totale pagine:       " + totPagine);
                    System.out.printf("Media pagine:        %.1f%n", mediaPagine);
                    System.out.println("Libro più antico:    " + annoMin);
                    System.out.println("Libro più recente:   " + annoMax);
                    System.out.println("Libri disponibili:   " + disponibili + "/" + biblioteca.size());
                    System.out.println("Libro più costoso:   " + piuCostoso.getTitolo() + 
                        " (€" + String.format("%.2f", piuCostoso.getPrezzo()) + ")");
                    break;
                    
                case 0:  // Esci
                    System.out.println("\n👋 Grazie per aver usato Biblioteca Personale!");
                    break;
                    
                default:
                    System.out.println("\n❌ Scelta non valida!");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Errore: " + e.getMessage());
            System.out.println("   Riprova con dati validi.");
        } catch (Exception e) {
            System.out.println("\n❌ Errore imprevisto: " + e.getMessage());
            input.nextLine();  // Pulisce buffer
        }
        
    } while (scelta != 0);
    
    input.close();
}
 public static double calcolaPrezzoTotale(ArrayList<Libro> libri) {
        double totale = 0;
        for (Libro libro : libri) {
            totale += libro.getPrezzo();
        }
        return totale;
    }
    
    public static Libro trovaPiuCostoso(ArrayList<Libro> libri) {
        if (libri.isEmpty()) return null;
        
        Libro max = libri.get(0);
        for (Libro libro : libri) {
            if (libro.getPrezzo() > max.getPrezzo()) {
                max = libro;
            }
        }
        return max;
    }
    
    public static Libro trovaPiuAntico(ArrayList<Libro> libri) {
        if (libri.isEmpty()) return null;
        
        Libro antico = libri.get(0);
        for (Libro libro : libri) {
            if (libro.getAnnoPubblicazione() < antico.getAnnoPubblicazione()) {
                antico = libro;
            }
        }
        return antico;
    }
    
    public static ArrayList<Libro> filtraDisponibili(ArrayList<Libro> libri) {
        ArrayList<Libro> disponibili = new ArrayList<>();
        for (Libro libro : libri) {
            if (libro.isDisponibile()) {
                disponibili.add(libro);
            }
        }
        return disponibili;
    }
    
    public static ArrayList<Libro> filtraPerGenere(ArrayList<Libro> libri, String genere) {
        ArrayList<Libro> risultato = new ArrayList<>();
        for (Libro libro : libri) {
            if (libro.getGenere().equalsIgnoreCase(genere)) {
                risultato.add(libro);
            }
        }
        return risultato;
    }

}