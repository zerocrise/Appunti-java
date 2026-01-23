package model;

import interfaces.Valutabile;
import java.io.Serializable;
import java.util.ArrayList;

public class Libro extends Pubblicazione implements Valutabile, Serializable {
    private static final long serialVersionUID = 1L;

    private String autore;
    private String genere;
    private String isbn;
    private ArrayList<Integer> valutazioni;

    public Libro(String titolo, String autore, int anno, String genere, int pagine, double prezzo) {
        super(titolo, anno, pagine, prezzo);
        setAutore(autore);
        setGenere(genere);
        this.valutazioni = new ArrayList<>();
    }

    public Libro(String titolo, String autore, int anno, String genere, int pagine, double prezzo, String isbn) {
        this(titolo, autore, anno, genere, pagine, prezzo);
        this.isbn = isbn;
    }

    @Override
    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📚 LIBRO");
        System.out.println("Titolo: " + titolo);
        System.out.println("Autore: " + autore);
        System.out.println("Genere: " + genere);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Pagine: " + numeroPagine);
        if (isbn != null) {
            System.out.println("ISBN: " + isbn);
        }
        System.out.printf("Prezzo: €%.2f (Scontato: €%.2f)%n", prezzo, getPrezzoScontato());
        if (!valutazioni.isEmpty()) {
            System.out.printf("Valutazione: %.1f/5 (%d voti) - %s%n",
                    getMediaValutazioni(), getNumeroValutazioni(), getClassificazione());
        }
        System.out.println("Disponibile: " + (disponibile ? "✅ Sì" : "❌ No"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String getTipo() {
        return "Libro";
    }

    @Override
    public double calcolaSconto() {
        if (annoPubblicazione < 1950) return prezzo * 0.30;
        if (annoPubblicazione < 2000) return prezzo * 0.20;
        return prezzo * 0.10;
    }

    @Override
    public void aggiungiValutazione(int voto) {
        if (voto < 1 || voto > 5) {
            throw new IllegalArgumentException("Voto deve essere 1-5");
        }
        valutazioni.add(voto);
    }

    @Override
    public double getMediaValutazioni() {
        if (valutazioni.isEmpty()) return 0;
        int somma = 0;
        for (int v : valutazioni) somma += v;
        return somma / (double) valutazioni.size();
    }

    @Override
    public int getNumeroValutazioni() {
        return valutazioni.size();
    }

    public boolean isLungo() {
        return numeroPagine > 500;
    }

    public boolean isRecente() {
        return annoPubblicazione > 2000;
    }

    public String getAutore() { return autore; }
    public String getGenere() { return genere; }
    public String getIsbn() { return isbn; }

    public void setAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            throw new IllegalArgumentException("Autore non valido");
        }
        this.autore = autore.trim();
    }

    public void setGenere(String genere) {
        this.genere = (genere != null) ? genere.trim() : "Non specificato";
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}