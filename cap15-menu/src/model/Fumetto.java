package model;

import java.io.Serializable;

public class Fumetto extends Pubblicazione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String autore;
    private String disegnatore;
    private String serie;
    private int numeroVolume;
    private boolean aColori;

    public Fumetto(String titolo, String autore, String disegnatore, int anno,
                   String serie, int numeroVolume, int pagine, double prezzo, boolean aColori) {
        super(titolo, anno, pagine, prezzo);
        setAutore(autore);
        setDisegnatore(disegnatore);
        setSerie(serie);
        setNumeroVolume(numeroVolume);
        this.aColori = aColori;
    }

    @Override
    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("🎨 FUMETTO");
        System.out.println("Titolo: " + titolo);
        System.out.println("Serie: " + serie + " - Volume " + numeroVolume);
        System.out.println("Autore: " + autore);
        System.out.println("Disegnatore: " + disegnatore);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Pagine: " + numeroPagine);
        System.out.println("Formato: " + (aColori ? "A colori" : "Bianco e nero"));
        System.out.printf("Prezzo: €%.2f%n", prezzo);
        if (isPrimoVolume()) {
            System.out.println("⭐ PRIMO VOLUME DELLA SERIE!");
        }
        System.out.println("Disponibile: " + (disponibile ? "✅ Sì" : "❌ No"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String getTipo() {
        return "Fumetto";
    }

    @Override
    public double calcolaSconto() {
        if (isPrimoVolume()) return prezzo * 0.15;
        return prezzo * 0.10;
    }

    public boolean isPrimoVolume() {
        return numeroVolume == 1;
    }

    public String getInfoCompleta() {
        return serie + " #" + numeroVolume + " - " + titolo;
    }

    public String getAutore() { return autore; }
    public String getDisegnatore() { return disegnatore; }
    public String getSerie() { return serie; }
    public int getNumeroVolume() { return numeroVolume; }
    public boolean isAColori() { return aColori; }

    public void setAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            throw new IllegalArgumentException("Autore non valido");
        }
        this.autore = autore.trim();
    }

    public void setDisegnatore(String disegnatore) {
        if (disegnatore == null || disegnatore.trim().isEmpty()) {
            throw new IllegalArgumentException("Disegnatore non valido");
        }
        this.disegnatore = disegnatore.trim();
    }

    public void setSerie(String serie) {
        this.serie = (serie != null) ? serie.trim() : "Stand-alone";
    }

    public void setNumeroVolume(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero volume deve essere > 0");
        }
        this.numeroVolume = numero;
    }

    public void setAColori(boolean aColori) {
        this.aColori = aColori;
    }
}