public class Rivista extends Pubblicazione {
    // Attributi specifici della Rivista
    private String editore;
    private int numeroEdizione;
    private String periodicita;  // "Settimanale", "Mensile", "Trimestrale"

    // Costruttore
    public Rivista(String titolo, String editore, int anno, int numeroEdizione,
                   String periodicita, int pagine, double prezzo) {
        super(titolo, anno, pagine, prezzo);
        setEditore(editore);
        setNumeroEdizione(numeroEdizione);
        setPeriodicita(periodicita);
    }

    // Getter e Setter
    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        if (editore == null || editore.trim().isEmpty()) {
            throw new IllegalArgumentException("Editore non può essere vuoto");
        }
        this.editore = editore.trim();
    }

    public int getNumeroEdizione() {
        return numeroEdizione;
    }

    public void setNumeroEdizione(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero edizione deve essere > 0");
        }
        this.numeroEdizione = numero;
    }

    public String getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(String periodicita) {
        this.periodicita = (periodicita != null) ? periodicita.trim() : "Mensile";
    }

    // Metodi specifici
    public boolean isNumeroSpeciale() {
        return numeroEdizione % 100 == 0;  // Es: numero 100, 200, 300
    }

    // Override
    @Override
    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📰 RIVISTA");
        System.out.println("Titolo: " + titolo);
        System.out.println("Editore: " + editore);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Numero: " + numeroEdizione);
        System.out.println("Periodicità: " + periodicita);
        System.out.println("Pagine: " + numeroPagine);
        System.out.printf("Prezzo: €%.2f%n", prezzo);
        if (isNumeroSpeciale()) {
            System.out.println("⭐ EDIZIONE SPECIALE!");
        }
        System.out.println("Stato: " + (disponibile ? "✅ Disponibile" : "❌ Non disponibile"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String toString() {
        return "Rivista: " + titolo + " #" + numeroEdizione + " (" + editore + ")";
    }
}
