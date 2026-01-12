public class Libro {
    // Attributi
    String titolo;
    String autore;
    int annoPubblicazione;
    String genere;
    int numeroPagine;
    boolean disponibile;
    
    // Costruttore
    public Libro(String titolo, String autore, int anno, String genere, int pagine) {
        this.titolo = titolo;
        this.autore = autore;
        this.annoPubblicazione = anno;
        this.genere = genere;
        this.numeroPagine = pagine;
        this.disponibile = true;
    }
    
    // Metodi
    void stampaInfo() {
        System.out.println("\n📚 " + titolo);
        System.out.println("   Autore: " + autore);
        System.out.println("   Anno: " + annoPubblicazione + " | Genere: " + genere);
        System.out.println("   Pagine: " + numeroPagine);
        System.out.println("   Stato: " + (disponibile ? "✅ Disponibile" : "❌ Non disponibile"));
    }
    
    void stampaInfoCompatta() {
        String stato = disponibile ? "✅" : "❌";
        System.out.println(stato + " " + titolo + " - " + autore + " (" + annoPubblicazione + ")");
    }
    
    int calcolaEta() {
        return 2024 - annoPubblicazione;
    }
    
    double calcolaTempoLettura() {
        return numeroPagine / 50.0;
    }
    
    void presta() {
        if (disponibile) {
            disponibile = false;
            System.out.println("✅ \"" + titolo + "\" prestato con successo");
        } else {
            System.out.println("❌ \"" + titolo + "\" non disponibile");
        }
    }
    
    void restituisci() {
        disponibile = true;
        System.out.println("✅ \"" + titolo + "\" restituito con successo");
    }
    
    boolean contiene(String parola) {
        String p = parola.toLowerCase();
        return titolo.toLowerCase().contains(p) || autore.toLowerCase().contains(p);
    }
}