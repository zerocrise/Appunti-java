public class Libro extends Pubblicazione {
    // Attributi specifici del Libro
    private String autore;
    private String genere;
    private String isbn;

    // Costruttore
    public Libro(String titolo, String autore, int anno, String genere,
                 int pagine, double prezzo) {
        // Chiama il costruttore della superclasse
        super(titolo, anno, pagine, prezzo);

        // Inizializza attributi specifici
        setAutore(autore);
        setGenere(genere);
    }

    // Costruttore con ISBN
    public Libro(String titolo, String autore, int anno, String genere,
                 int pagine, double prezzo, String isbn) {
        this(titolo, autore, anno, genere, pagine, prezzo);
        this.isbn = isbn;
    }

    // Getter e Setter specifici
    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            throw new IllegalArgumentException("Autore non può essere vuoto");
        }
        this.autore = autore.trim();
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = (genere != null) ? genere.trim() : "Non specificato";
    }

    public String getIsbn() {
        return isbn;
    }

    // Metodi specifici del Libro
    public boolean isLungo() {
        return numeroPagine > 500;
    }

    public boolean isRecente() {
        return annoPubblicazione > 2000;
    }

    // Override del metodo stampaInfo
    @Override
    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📚 LIBRO");
        System.out.println("Titolo: " + titolo);
        System.out.println("Autore: " + autore);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Genere: " + genere);
        System.out.println("Pagine: " + numeroPagine);
        if (isbn != null) {
            System.out.println("ISBN: " + isbn);
        }
        System.out.printf("Prezzo: €%.2f (IVA: €%.2f)%n", prezzo, getPrezzoConIVA());
        System.out.println("Stato: " + (disponibile ? "✅ Disponibile" : "❌ Non disponibile"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String toString() {
        return "Libro: " + titolo + " - " + autore + " (" + annoPubblicazione + ")";
    }
}
