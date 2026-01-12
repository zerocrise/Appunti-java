public class Libro {
    // Attributi PRIVATI
    private String titolo;
    private String autore;
    private int annoPubblicazione;
    private String genere;
    private int numeroPagine;
    private boolean disponibile;
    private double prezzo;
    
    // Costruttore
    public Libro(String titolo, String autore, int anno, String genere, int pagine, double prezzo) {
        setTitolo(titolo);           // Usa setter per validazione
        setAutore(autore);
        setAnnoPubblicazione(anno);
        setGenere(genere);
        setNumeroPagine(pagine);
        setPrezzo(prezzo);
        this.disponibile = true;
    }
    
    // GETTER
    public String getTitolo() {
        return titolo;
    }
    
    public String getAutore() {
        return autore;
    }
    
    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
    
    public String getGenere() {
        return genere;
    }
    
    public int getNumeroPagine() {
        return numeroPagine;
    }
    
    public boolean isDisponibile() {  // is per boolean
        return disponibile;
    }
    
    public double getPrezzo() {
        return prezzo;
    }
    
    // SETTER con VALIDAZIONE
    public void setTitolo(String titolo) {
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo non può essere vuoto");
        }
        this.titolo = titolo.trim();
    }
    
    public void setAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            throw new IllegalArgumentException("Autore non può essere vuoto");
        }
        this.autore = autore.trim();
    }
    
    public void setAnnoPubblicazione(int anno) {
        if (anno < 0 || anno > 2024) {
            throw new IllegalArgumentException("Anno non valido: " + anno);
        }
        this.annoPubblicazione = anno;
    }
    
    public void setGenere(String genere) {
        if (genere == null || genere.trim().isEmpty()) {
            this.genere = "Non specificato";
        } else {
            this.genere = genere.trim();
        }
    }
    
    public void setNumeroPagine(int pagine) {
        if (pagine <= 0) {
            throw new IllegalArgumentException("Numero pagine deve essere > 0");
        }
        this.numeroPagine = pagine;
    }
    
    public void setPrezzo(double prezzo) {
        if (prezzo < 0) {
            throw new IllegalArgumentException("Prezzo non può essere negativo");
        }
        this.prezzo = prezzo;
    }
    
    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }
    
    // METODI BUSINESS
    public void presta() {
        if (!disponibile) {
            System.out.println("❌ \"" + titolo + "\" non è disponibile");
            return;
        }
        disponibile = false;
        System.out.println("✅ \"" + titolo + "\" prestato con successo");
    }
    
    public void restituisci() {
        if (disponibile) {
            System.out.println("⚠️  \"" + titolo + "\" è già disponibile");
            return;
        }
        disponibile = true;
        System.out.println("✅ \"" + titolo + "\" restituito con successo");
    }
    
    public int calcolaEta() {
        return 2024 - annoPubblicazione;
    }
    
    public double calcolaTempoLettura() {
        return numeroPagine / 50.0;
    }
    
    public boolean isLungo() {
        return numeroPagine > 500;
    }
    
    public boolean isRecente() {
        return annoPubblicazione > 2000;
    }
    
    public double getPrezzoConIVA() {
        return prezzo * 1.22;
    }
    
    public void applicaSconto(double percentuale) {
        if (percentuale < 0 || percentuale > 100) {
            throw new IllegalArgumentException("Percentuale sconto non valida");
        }
        prezzo = prezzo * (1 - percentuale / 100.0);
    }
    
    public boolean contiene(String parola) {
        String p = parola.toLowerCase();
        return titolo.toLowerCase().contains(p) || 
               autore.toLowerCase().contains(p);
    }
    
    // METODI DI VISUALIZZAZIONE
    public void stampaInfo() {
        System.out.println("\n📚 " + titolo);
        System.out.println("   Autore: " + autore);
        System.out.println("   Anno: " + annoPubblicazione + " | Genere: " + genere);
        System.out.println("   Pagine: " + numeroPagine);
        System.out.printf("   Prezzo: €%.2f (con IVA: €%.2f)%n", prezzo, getPrezzoConIVA());
        System.out.println("   Stato: " + (disponibile ? "✅ Disponibile" : "❌ Non disponibile"));
    }
    
    public void stampaInfoCompatta() {
        String stato = disponibile ? "✅" : "❌";
        System.out.printf("%s %s - %s (%d) - €%.2f%n", 
            stato, titolo, autore, annoPubblicazione, prezzo);
    }
    
    @Override
    public String toString() {
        return titolo + " - " + autore + " (" + annoPubblicazione + ")";
    }
}