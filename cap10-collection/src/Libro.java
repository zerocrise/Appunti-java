// Classe Libro completa
public class Libro implements Comparable<Libro> {
    private String isbn;
    private String titolo;
    private String autore;
    private int annoPubblicazione;
    private int numeroPagine;

    public Libro(String isbn, String titolo, String autore,
                 int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public int getAnnoPubblicazione() { return annoPubblicazione; }
    public int getNumeroPagine() { return numeroPagine; }

    @Override
    public int compareTo(Libro altro) {
        return this.titolo.compareTo(altro.titolo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Libro libro = (Libro) obj;
        return isbn.equals(libro.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    public String getInfoCompleta() {
        return String.format("%s - \"%s\" di %s (%d) - %d pp.",
                isbn, titolo, autore, annoPubblicazione, numeroPagine);
    }
}
