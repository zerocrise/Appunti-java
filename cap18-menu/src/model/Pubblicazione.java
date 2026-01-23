package model;

import interfaces.Prestabile;
import interfaces.Scontabile;
import java.io.Serializable;

public abstract class Pubblicazione implements Prestabile, Scontabile, Serializable {
    private static final long serialVersionUID = 1L;

    protected String titolo;
    protected int annoPubblicazione;
    protected int numeroPagine;
    protected boolean disponibile;
    protected double prezzo;

    public Pubblicazione(String titolo, int anno, int pagine, double prezzo) {
        setTitolo(titolo);
        setAnnoPubblicazione(anno);
        setNumeroPagine(pagine);
        setPrezzo(prezzo);
        this.disponibile = true;
    }

    // Metodi astratti
    public abstract void stampaInfo();
    public abstract String getTipo();
    public abstract double calcolaSconto();

    // Metodi concreti comuni
    public int calcolaEta() {
        return 2024 - annoPubblicazione;
    }

    public double calcolaTempoLettura() {
        return numeroPagine / 50.0;
    }

    public double getPrezzoConIVA() {
        return prezzo * 1.22;
    }

    // Implementazione Prestabile
    @Override
    public void presta() {
        if (!disponibile) {
            System.out.println("❌ \"" + titolo + "\" non disponibile");
            return;
        }
        disponibile = false;
        System.out.println("✅ \"" + titolo + "\" prestato");
    }

    @Override
    public void restituisci() {
        if (disponibile) {
            System.out.println("⚠️  \"" + titolo + "\" già disponibile");
            return;
        }
        disponibile = true;
        System.out.println("✅ \"" + titolo + "\" restituito");
    }

    @Override
    public boolean isDisponibile() {
        return disponibile;
    }

    // Implementazione Scontabile
    @Override
    public double getPrezzoScontato() {
        return prezzo - calcolaSconto();
    }

    // Getter e Setter
    public String getTitolo() { return titolo; }
    public int getAnnoPubblicazione() { return annoPubblicazione; }
    public int getNumeroPagine() { return numeroPagine; }
    public double getPrezzo() { return prezzo; }

    public void setTitolo(String titolo) {
        if (titolo == null || titolo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo non valido");
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
            throw new IllegalArgumentException("Prezzo non valido");
        }
        this.prezzo = prezzo;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return getTipo() + ": " + titolo + " (" + annoPubblicazione + ")";
    }
}