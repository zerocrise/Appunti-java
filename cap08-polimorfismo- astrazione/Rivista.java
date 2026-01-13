public class Rivista extends Pubblicazione {
    private String editore;
    private int numeroEdizione;
    private String periodicita;
    
    public Rivista(String titolo, String editore, int anno, int numeroEdizione,
                   String periodicita, int pagine, double prezzo) {
        super(titolo, anno, pagine, prezzo);
        this.editore = editore;
        this.numeroEdizione = numeroEdizione;
        this.periodicita = periodicita;
    }
    
    @Override
    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📰 RIVISTA");
        System.out.println("Titolo: " + titolo);
        System.out.println("Editore: " + editore);
        System.out.println("Numero: " + numeroEdizione);
        System.out.println("Periodicità: " + periodicita);
        System.out.println("Anno: " + annoPubblicazione);
        System.out.println("Pagine: " + numeroPagine);
        System.out.printf("Prezzo: €%.2f%n", prezzo);
        System.out.println("═══════════════════════════════════");
    }
    
    @Override
    public String getTipo() {
        return "Rivista";
    }
    
    @Override
    public double calcolaSconto() {
        // Logica specifica per riviste
        if (numeroEdizione > 100) {
            return prezzo * 0.05;  // 5% riviste recenti
        }
        return prezzo * 0.40;  // 40% riviste vecchie
    }
    
    public String getEditore() { return editore; }
    public int getNumeroEdizione() { return numeroEdizione; }
}