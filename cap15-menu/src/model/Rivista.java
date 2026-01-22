package model;

import java.io.Serializable;

public class Rivista extends Pubblicazione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String editore;
    private int numeroEdizione;
    private String periodicita;

    public Rivista(String titolo, String editore, int anno, int numeroEdizione,
                   String periodicita, int pagine, double prezzo) {
        super(titolo, anno, pagine, prezzo);
        setEditore(editore);
        setNumeroEdizione(numeroEdizione);
        setPeriodicita(periodicita);
    }

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
        System.out.println("Disponibile: " + (disponibile ? "✅ Sì" : "❌ No"));
        System.out.println("═══════════════════════════════════");
    }

    @Override
    public String getTipo() {
        return "Rivista";
    }

    @Override
    public double calcolaSconto() {
        if (numeroEdizione > 100) return prezzo * 0.05;
        return prezzo * 0.40;
    }

    public boolean isNumeroSpeciale() {
        return numeroEdizione % 100 == 0;
    }

    public String getEditore() { return editore; }
    public int getNumeroEdizione() { return numeroEdizione; }
    public String getPeriodicita() { return periodicita; }

    public void setEditore(String editore) {
        if (editore == null || editore.trim().isEmpty()) {
            throw new IllegalArgumentException("Editore non valido");
        }
        this.editore = editore.trim();
    }

    public void setNumeroEdizione(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero edizione deve essere > 0");
        }
        this.numeroEdizione = numero;
    }

    public void setPeriodicita(String periodicita) {
        this.periodicita = (periodicita != null) ? periodicita.trim() : "Mensile";
    }
}