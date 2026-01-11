public class Pubblicazione {
    // Attributi comuni a tutte le pubblicazioni
    protected String titolo;
    protected int annoPubblicazione;
    protected int numeroPagine;
    protected boolean disponibile;
    protected double prezzo;

    // Costruttore
    public Pubblicazione(String titolo, int anno, int pagine, double prezzo) {
        setTitolo(titolo);
        setAnnoPubblicazione(anno);
        setNumeroPagine(pagine);
        setPrezzo(prezzo);
        this.disponibile = true;
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public double getPrezzo() {
        return prezzo;
    }

    // Setter con validazione
    public void setTitolo(String titolo) {
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo non può essere vuoto");
        }
        this.titolo = titolo.trim();
    }

    public void setAnnoPubblicazione(int anno) {
        if (anno < 0 || anno > 2024) {
            throw new IllegalArgumentException("Anno non valido");
        }
        this.annoPubblicazione = anno;
    }

    public void setNumeroPagine(int pagine) {
        if (pagine <= 0) {
            throw new IllegalArgumentException("Pagine deve essere > 0");
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

    // Metodi comuni
    public int calcolaEta() {
        return 2024 - annoPubblicazione;
    }

    public double calcolaTempoLettura() {
        return numeroPagine / 50.0;
    }

    public double getPrezzoConIVA() {
        return prezzo * 1.22;
    }

    public void presta() {
        if (!disponibile) {
            System.out.println("❌ \"" + titolo + "\" non disponibile");
            return;
        }
        disponibile = false;
        System.out.println("✅ \"" + titolo + "\" prestato");
    }

    public void restituisci() {
        disponibile = true;
        System.out.println("✅ \"" + titolo + "\" restituito");
    }

    public void stampaInfo() {
        System.out.println("Titolo: " + titolo);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Pagine: " + numeroPagine);
        System.out.printf("Prezzo: €%.2f%n", prezzo);
        System.out.println("Disponibile: " + (disponibile ? "Sì" : "No"));
    }
}
