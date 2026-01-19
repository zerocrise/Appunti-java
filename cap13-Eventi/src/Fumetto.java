public class Fumetto extends Pubblicazione {
    // Attributi specifici del Fumetto
    private String autore;
    private String disegnatore;
    private String serie;
    private int numeroVolume;
    private boolean aColori;

    // Costruttore
    public Fumetto(String titolo, String autore, String disegnatore, int anno,
                   String serie, int numeroVolume, int pagine, double prezzo, boolean aColori) {
        super(titolo, anno, pagine, prezzo);
        setAutore(autore);
        setDisegnatore(disegnatore);
        setSerie(serie);
        setNumeroVolume(numeroVolume);
        this.aColori = aColori;
    }

    // Getter e Setter
    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        if (autore == null || autore.trim().isEmpty()) {
            throw new IllegalArgumentException("Autore non può essere vuoto");
        }
        this.autore = autore.trim();
    }

    public String getDisegnatore() {
        return disegnatore;
    }

    public void setDisegnatore(String disegnatore) {
        if (disegnatore == null || disegnatore.trim().isEmpty()) {
            throw new IllegalArgumentException("Disegnatore non può essere vuoto");
        }
        this.disegnatore = disegnatore.trim();
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = (serie != null) ? serie.trim() : "Stand-alone";
    }

    public int getNumeroVolume() {
        return numeroVolume;
    }

    public void setNumeroVolume(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero volume deve essere > 0");
        }
        this.numeroVolume = numero;
    }

    public boolean isAColori() {
        return aColori;
    }

    public void setAColori(boolean aColori) {
        this.aColori = aColori;
    }

    // Metodi specifici
    public boolean isPrimoVolume() {
        return numeroVolume == 1;
    }

    public String getInfoCompleta() {
        return serie + " #" + numeroVolume + " - " + titolo;
    }

    // Override
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
        System.out.println("Stato: " + (disponibile ? "✅ Disponibile" : "❌ Non disponibile"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String toString() {
        return "Fumetto: " + serie + " #" + numeroVolume + " - " + titolo;
    }
}
