import java.util.Scanner;

public class Biblioteca {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Intestazione
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  BIBLIOTECA PERSONALE - Versione 2.0  ║");
        System.out.println("║      Catalogatore Interattivo          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        
        // Input dati libro
        System.out.println("Inserisci i dati del nuovo libro:");
        System.out.println("─────────────────────────────────────────");
        
        System.out.print("Titolo: ");
        String titolo = input.nextLine();
        
        System.out.print("Autore: ");
        String autore = input.nextLine();
        
        System.out.print("Anno di pubblicazione: ");
        int anno = input.nextInt();
        input.nextLine();  // Consuma l'invio
        
        System.out.print("Genere: ");
        String genere = input.nextLine();
        
        System.out.print("Numero di pagine: ");
        int pagine = input.nextInt();
        
        System.out.print("Prezzo (€): ");
        double prezzo = input.nextDouble();
        
        System.out.print("Disponibile? (true/false): ");
        boolean disponibile = input.nextBoolean();
        
        // Calcoli
        int etaLibro = 2024 - anno;
        double prezzoConIva = prezzo * 1.22;
        
        // Output formattato
        System.out.println();
        System.out.println("═════════════════════════════════════════");
        System.out.println("📚 SCHEDA LIBRO REGISTRATO");
        System.out.println("═════════════════════════════════════════");
        System.out.println("Titolo:           " + titolo);
        System.out.println("Autore:           " + autore);
        System.out.println("Anno:             " + anno + " (" + etaLibro + " anni fa)");
        System.out.println("Genere:           " + genere);
        System.out.println("Pagine:           " + pagine);
        System.out.println("Prezzo:           €" + prezzo);
        System.out.println("Prezzo con IVA:   €" + prezzoConIva);
        System.out.println("Stato:            " + (disponibile ? "Disponibile" : "Non disponibile"));
        System.out.println("═════════════════════════════════════════");
        
        // Statistiche
        double tempoLettura = pagine / 50.0;  // 50 pagine all'ora
        System.out.println();
        System.out.println("📊 STATISTICHE:");
        System.out.println("Tempo di lettura stimato: " + tempoLettura + " ore");
        
        input.close();
    }
}
